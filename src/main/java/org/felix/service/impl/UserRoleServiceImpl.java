package org.felix.service.impl;

import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysUserRoleMapper;
import org.felix.model.entity.SysUserRole;
import org.felix.model.vo.req.UserRoleOperationReqVO;
import org.felix.service.UserRoleService;
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
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<String> getRolesIdByUserId(String userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    public int removeByUserId(String userId) {
        return sysUserRoleMapper.removeByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {

        if (vo.getRoleIds() == null || vo.getRoleIds().isEmpty()) {
            return;
        }

        Date createTime = new Date();
        List<SysUserRole> list = new ArrayList<>();
        String userId = vo.getUserId();
        vo.getRoleIds().forEach(roleId->{
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setId(UUID.randomUUID().toString());
            sysUserRole.setCreateTime(createTime);
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        });
        sysUserRoleMapper.removeByUserId(vo.getUserId());
        int count = sysUserRoleMapper.batchUserRole(list);
        if (count == 0) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public int removeByRoleId(String roleId) {
        return sysUserRoleMapper.removeByRoleId(roleId);
    }

    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {
        return sysUserRoleMapper.getUserIdsByRoleIds(roleIds);
    }
}
