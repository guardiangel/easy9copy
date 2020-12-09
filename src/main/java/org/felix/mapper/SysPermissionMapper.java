package org.felix.mapper;

import org.felix.model.entity.SysPermission;

import java.util.List;

/**
 * @author Felix
 */
public interface SysPermissionMapper {

    List<SysPermission> selectInfoByIds(List<String> ids);

    List<SysPermission> selectInfoByUserIds(String userId);

    List<SysPermission> selectAll();

    SysPermission selectByPrimaryKey(String id);

    int insertSelective(SysPermission record);

    List<SysPermission> selectChild(String pid);

    int updateByPrimaryKeySelective(SysPermission record);
}
