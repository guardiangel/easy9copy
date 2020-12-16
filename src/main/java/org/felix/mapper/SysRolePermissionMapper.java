package org.felix.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.felix.model.entity.SysRolePermission;

import java.util.List;

/**
 * @author Felix
 */
public interface SysRolePermissionMapper {

    List<String> getPermissionIdsByRoles(List<String> roleIds);

    int removeByRoleId(String roleId);

    List<String> getPermissionIdsByRoleId(String roleId);

    int batchRolePermission(List<SysRolePermission> list);

    int removeByPermissionId(String permissionId);

    List<String> getRoleIds(String permissionId);


}
