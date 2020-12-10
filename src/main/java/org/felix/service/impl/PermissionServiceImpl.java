package org.felix.service.impl;

import com.github.pagehelper.PageHelper;
import org.felix.aop.constants.Constant;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysPermissionMapper;
import org.felix.model.entity.SysPermission;
import org.felix.model.vo.req.PermissionAddReqVO;
import org.felix.model.vo.req.PermissionPageReqVO;
import org.felix.model.vo.req.PermissionUpdateReqVO;
import org.felix.model.vo.resp.PageVO;
import org.felix.model.vo.resp.PermissionRespNode;
import org.felix.service.PermissionService;
import org.felix.service.RedisService;
import org.felix.service.RolePermissionService;
import org.felix.service.UserRoleService;
import org.felix.utils.PageUtils;
import org.felix.utils.TokenSettings;
import org.felix.utils.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private TokenSettings tokenSettings;

    @Override
    public List<SysPermission> getPermission(String userId) {
        return sysPermissionMapper.selectInfoByUserIds(userId);
    }

    @Override
    public Set<String> getPermissionsByUserId(String userId) {

        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> list = getPermission(userId);
        list.forEach(sysPermission -> {
            if (!StringUtils.isEmpty(sysPermission.getPerms())) {
                permissionSet.add(sysPermission.getPerms());
            }
        });

        return permissionSet;
    }

    @Override
    public List<PermissionRespNode> permissionTreeList(String userId) {
        return getTree(getPermission(userId), true);
    }

    private List<PermissionRespNode> getTree(List<SysPermission> permissions, boolean type) {

        List<PermissionRespNode> result = new ArrayList<>();
        if (permissions == null || permissions.isEmpty()) {
            return result;
        }
        permissions.forEach(sysPermission -> {
            if (Constant.PERMISSION_PID.equals(sysPermission.getPid())) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                if (type) {
                    permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), permissions));
                } else {
                    permissionRespNode.setChildren(getChildAll(sysPermission.getId(), permissions));
                }
                result.add(permissionRespNode);
            }
        });

        return result;
    }

    /**
     * 递归遍历所有
     *
     * @param id
     * @param permissions
     * @return
     */
    private List<?> getChildAll(String id, List<SysPermission> permissions) {

        List<PermissionRespNode> list = new ArrayList<>();
        permissions.forEach(sysPermission -> {
            if (sysPermission.getPid().equals(id)) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildAll(sysPermission.getId(), permissions));
                list.add(permissionRespNode);
            }
        });

        return list;
    }

    /**
     * 只递归获取目录和菜单
     *
     * @param id
     * @param permissions
     * @return
     */
    private List<?> getChildExcBtn(String id, List<SysPermission> permissions) {

        List<PermissionRespNode> list = new ArrayList<>();
        permissions.forEach(sysPermission -> {
            if (sysPermission.getPid().equals(id) && sysPermission.getType() != 3) {
                PermissionRespNode permissionRespNode = new PermissionRespNode();
                BeanUtils.copyProperties(sysPermission, permissionRespNode);
                permissionRespNode.setTitle(sysPermission.getName());
                permissionRespNode.setChildren(getChildExcBtn(sysPermission.getId(), permissions));
                list.add(permissionRespNode);
            }
        });

        return list;
    }

    @Override
    public List<PermissionRespNode> selectAllByTree() {
        return getTree(selectAll(), false);
    }

    @Override
    public List<SysPermission> selectAll() {

        List<SysPermission> result = sysPermissionMapper.selectAll();
        if (result != null) {
            result.forEach(sysPermission -> {
                SysPermission parent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
                if (parent != null) {
                    sysPermission.setPidName(parent.getName());
                }
            });
        }

        return result;
    }

    @Override
    public SysPermission addPermission(PermissionAddReqVO vo) {

        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(vo, sysPermission);
        verifyForm(sysPermission);
        sysPermission.setId(Tool.getPrimaryKey());
        sysPermission.setCreateTime(new Date());
        int count = sysPermissionMapper.insertSelective(sysPermission);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }

        return sysPermission;
    }

    /**
     * 验证表单
     *
     * @param sysPermission
     */
    private void verifyForm(SysPermission sysPermission) {
        verifyFormPid(sysPermission);
        //编辑，有子节点，则不允许更新
        if (!StringUtils.isEmpty(sysPermission.getId())) {
            List<SysPermission> list = sysPermissionMapper.selectChild(sysPermission.getId());
            if (list != null && !list.isEmpty()) {
                throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }
    }

    /**
     * 操作后的菜单类型是目录的时候 父级必须为目录
     * 操作后的菜单类型是菜单的时候，父类必须为目录类型
     * 操作后的菜单类型是按钮的时候 父类必须为菜单类型
     *
     * @param sysPermission
     */
    private void verifyFormPid(SysPermission sysPermission) {

        SysPermission parent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getId());
        switch (sysPermission.getType()) {
            case 1:
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!sysPermission.getPid().equals("0")) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if (parent == null || parent.getType() != 1) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getPerms())) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                if (StringUtils.isEmpty(sysPermission.getMethod())) {
                    throw new ServiceException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                }
                break;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String permissionId) {

        SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(permissionId);
        if (sysPermission == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        List<SysPermission> childs = sysPermissionMapper.selectChild(permissionId);
        if (childs != null && !childs.isEmpty()) {
            throw new ServiceException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        sysPermission.setDeleted(0);
        sysPermission.setUpdateTime(new Date());
        int count = sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }

        /**
         * 删除关联角色
         */
        rolePermissionService.removeByPermissionId(permissionId);
        List<String> roldIds = rolePermissionService.getRoleIds(permissionId);
        if ((roldIds != null && !roldIds.isEmpty())) {
            List<String> userIds = userRoleService.getUserIdsByRoleIds(roldIds);
            if (userIds != null && !userIds.isEmpty()) {
                userIds.forEach(userId -> {
                    redisService.set(Constant.JWT_REFRESH_KEY + userId, userId,
                            tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                    /**
                     * 清空签权
                     */
                    redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
                });
            }
        }
    }

    @Override
    public SysPermission detailInfo(String permissionId) {
        return sysPermissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public PageVO<SysPermission> pageInfo(PermissionPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysPermission> result = selectAll();
        return PageUtils.getPageVO(result);
    }

    /**
     * 获取所有的目录菜单树排除按钮(因为不管是新增或者修改,选择所属菜单目录的时候,都不可能选择到按钮,而且编辑的时候 所属目录不能选择自己和它的子类)
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<PermissionRespNode> selectAllMenuByTree(String permissionId) {
        List<SysPermission> list = selectAll();
        if (permissionId != null && list != null && !list.isEmpty()) {
            list.stream().filter(sysPermission ->
                    sysPermission.getId().equals(permissionId)).findFirst().ifPresent(list::remove);
        }

        return null;
    }

    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {

        SysPermission sysPermission = sysPermissionMapper.selectByPrimaryKey(vo.getId());
        if (sysPermission == null) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        SysPermission updatePermission = new SysPermission();
        BeanUtils.copyProperties(sysPermission, updatePermission);

        /**
         * 只有类型变更
         * 或者所属菜单变更
         */
        if (!sysPermission.getType().equals(vo.getType())
                || !sysPermission.getPid().equals(vo.getPid())) {
            verifyForm(updatePermission);
        }
        updatePermission.setUpdateTime(new Date());
        int count = sysPermissionMapper.updateByPrimaryKeySelective(updatePermission);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        /**
         * 说明这个菜单权限 权鉴标识发生变化
         * 所有管理这个菜单权限用户将重新刷新token
         */
        if (StringUtils.isEmpty(vo.getPerms()) &&
                !sysPermission.getPerms().equals(vo.getPerms())) {
            List<String> roleIds = rolePermissionService.getRoleIds(vo.getId());
            if (roleIds != null && !roleIds.isEmpty()) {
                List<String> userIds = userRoleService.getUserIdsByRoleIds(roleIds);
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
}
