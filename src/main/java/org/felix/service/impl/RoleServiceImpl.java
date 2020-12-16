package org.felix.service.impl;

import com.github.pagehelper.PageHelper;
import org.felix.aop.constants.Constant;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysRoleMapper;
import org.felix.mapper.SysUserRoleMapper;
import org.felix.model.entity.SysRole;
import org.felix.model.vo.req.RoleAddReqVO;
import org.felix.model.vo.req.RolePageReqVO;
import org.felix.model.vo.req.RolePermissionOperationReqVO;
import org.felix.model.vo.req.RoleUpdateReqVO;
import org.felix.model.vo.resp.PageVO;
import org.felix.model.vo.resp.PermissionRespNode;
import org.felix.service.*;
import org.felix.utils.PageUtils;
import org.felix.utils.TokenSettings;
import org.felix.utils.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisService redisService;

    @Resource
    private TokenSettings tokenSettings;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private RolePermissionService rolePermissionService;


    @Override
    public List<String> getRoleNames(String userId) {
        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (sysRoles == null || sysRoles.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>();
        sysRoles.forEach(sysRole -> {
            result.add(sysRole.getName());
        });

        return result;
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {

        List<String> roleIds = userRoleService.getRolesIdByUserId(userId);

        if (roleIds == null) {
            return null;
        }

        return sysRoleMapper.getRoleInfoByIds(roleIds);
    }

    @Override
    public List<SysRole> selectAllRoles() {

        return sysRoleMapper.selectAll(new RolePageReqVO());
    }

    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {

        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRole> sysRoles = sysRoleMapper.selectAll(vo);

        return PageUtils.getPageVO(sysRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {

        int count = sysRoleMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        rolePermissionService.removeByRoldId(id);
        userRoleService.removeByRoleId(id);
        List<String> userIds = sysUserRoleMapper.getInfoByUserIdByRoleId(id);
        if (userIds != null) {
            userIds.forEach(userId -> {
                redisService.set(Constant.JWT_REFRESH_KEY + userId, userId,
                        tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
            });
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public SysRole detailInfo(String id) {

        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        List<PermissionRespNode> permissionRespNodes = permissionService.selectAllByTree();
        Set<String> checkList = new HashSet<>();
        checkList.addAll(rolePermissionService.getPermissionidsByRoldId(sysRole.getId()));
        setChecked(permissionRespNodes, checkList);
        sysRole.setPermissionRespNodes(permissionRespNodes);

        return sysRole;
    }

    private void setChecked(List<PermissionRespNode> permissionRespNodes, Set<String> checkList) {

        permissionRespNodes.forEach(permissionRespNode -> {
            boolean flag = checkList.contains(permissionRespNode.getId()) && ((permissionRespNode.getChildren() == null
                    || permissionRespNode.getChildren().isEmpty()));
            if (flag) {
                permissionRespNode.setChecked(true);
                setChecked((List<PermissionRespNode>) permissionRespNode.getChildren(), checkList);
            }
        });

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(RoleAddReqVO vo) {

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(vo, sysRole);
        sysRole.setId(Tool.getPrimaryKey());
        sysRole.setCreateTime(new Date());
        int count = sysRoleMapper.insertSelective(sysRole);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        if (vo.getPermissions() != null) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }

        return sysRole;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(RoleUpdateReqVO vo, String accessToken) {

        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(vo.getId());
        if (sysRole == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        SysRole update = new SysRole();
        BeanUtils.copyProperties(vo, update);
        update.setCreateTime(new Date());
        int count = sysRoleMapper.updateByPrimaryKeySelective(update);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        rolePermissionService.removeByRoldId(sysRole.getId());
        if (vo.getPermissions() != null) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            List<String> userIds = sysUserRoleMapper.getInfoByUserIdByRoleId(vo.getId());
            if (userIds != null) {
                userIds.forEach(userId -> {
                    redisService.set(Constant.JWT_REFRESH_KEY + userId, userId,
                            tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                    //清空权鉴缓存
                    redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
                });
            }
        }

    }
}
