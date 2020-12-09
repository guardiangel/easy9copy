package org.felix.service.impl;

import org.felix.aop.constants.Constant;
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
import org.felix.utils.TokenSettings;
import org.felix.utils.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        permissions.forEach(sysPermission ->  {
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
     *只递归获取目录和菜单
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


        return null;
    }

    @Override
    public void delete(String permissionId) {

    }

    @Override
    public SysPermission detailInfo(String permissionId) {
        return null;
    }

    @Override
    public PageVO<SysPermission> pageInfo(PermissionPageReqVO vo) {
        return null;
    }

    @Override
    public List<PermissionRespNode> selectAllMenuByTree(String permissionId) {
        return null;
    }

    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {

    }
}
