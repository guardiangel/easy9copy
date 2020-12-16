package org.felix.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.felix.model.entity.SysUserRole;

import java.util.List;

/**
 * @author Felix
 */
public interface SysUserRoleMapper {

    List<String> getRoleIdsByUserId(String userId);

    int removeByUserId(String userId);

    int batchUserRole(List<SysUserRole> list);

    List<String> getInfoByUserIdByRoleId(String roleId);

    int removeByRoleId(String roleId);

    List<String> getUserIdsByRoleIds(List<String> roleIds);

}
