package org.felix.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.felix.model.entity.SysRole;
import org.felix.model.vo.req.RolePageReqVO;

import java.util.List;

/**
 * @author Felix
 */
public interface SysRoleMapper {

    List<SysRole> getRoleInfoByIds(List<String> ids);

    List<SysRole> selectAll(RolePageReqVO vo);

    /**
     * 根据ID删除角色信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    SysRole selectByPrimaryKey(String id);

    int insertSelective(SysRole record);

    int updateByPrimaryKeySelective(SysRole record);

}
