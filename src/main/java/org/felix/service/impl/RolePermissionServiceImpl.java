package org.felix.service.impl;

import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysRolePermissionMapper;
import org.felix.model.entity.SysRolePermission;
import org.felix.model.vo.req.RolePermissionOperationReqVO;
import org.felix.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Felix
 * @since
 * @Description
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<String> getPermissionIdsByRoles(List<String> roleIds) {
        return sysRolePermissionMapper.getPermissionIdsByRoles(roleIds);
    }

    @Override
    public int removeByRoldId(String roleId) {
        return sysRolePermissionMapper.removeByRoleId(roleId);
    }

    @Override
    public List<String> getPermissionidsByRoldId(String roleId) {
        return sysRolePermissionMapper.getPermissionIdsByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        Date createTime = new Date();
        List<SysRolePermission> list = new ArrayList<>();
        String roleId = vo.getRoleId();
        vo.getPermissionIds().forEach(permissionId -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setId(UUID.randomUUID().toString());
            sysRolePermission.setCreateTime(createTime);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(roleId);
            list.add(sysRolePermission);
        });
        sysRolePermissionMapper.removeByRoleId(vo.getRoleId());
        int count = sysRolePermissionMapper.batchRolePermission(list);
        if (count == 0) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public int removeByPermissionId(String permissionId) {

        return sysRolePermissionMapper.removeByPermissionId(permissionId);
    }

    @Override
    public List<String> getRoleIds(String permissionId) {

        return sysRolePermissionMapper.getRoleIds(permissionId);
    }
}
