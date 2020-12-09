package org.felix.service;

import org.felix.model.vo.req.RolePermissionOperationReqVO;

import java.util.List;

/**
 * @author Felix
 */
public interface RolePermissionService {

    List<String> getPermissionIdsByRoles(List<String> roleIds);

    int removeByRoldId(String roleId);

    List<String> getPermissionidsByRoldId(String roleId);

    void addRolePermission(RolePermissionOperationReqVO vo);

    int removeByPermissionId(String permissionId);

    List<String> getRoleIds(String permissionId);


}
