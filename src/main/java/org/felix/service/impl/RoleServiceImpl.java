package org.felix.service.impl;

import org.felix.mapper.SysRoleMapper;
import org.felix.mapper.SysUserRoleMapper;
import org.felix.model.entity.SysRole;
import org.felix.model.vo.req.RoleAddReqVO;
import org.felix.model.vo.req.RolePageReqVO;
import org.felix.model.vo.req.RoleUpdateReqVO;
import org.felix.model.vo.resp.PageVO;
import org.felix.service.*;
import org.felix.utils.TokenSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        return null;
    }

    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {
        return null;
    }

    @Override
    public List<SysRole> selectAllRoles() {
        return null;
    }

    @Override
    public PageVO<SysRole> pageInfo(RolePageReqVO vo) {
        return null;
    }

    @Override
    public void deletedRole(String id) {

    }

    @Override
    public SysRole detailInfo(String id) {
        return null;
    }

    @Override
    public SysRole addRole(RoleAddReqVO vo) {
        return null;
    }

    @Override
    public void updateRole(RoleUpdateReqVO vo, String accessToken) {

    }
}
