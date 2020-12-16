package org.felix.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.felix.model.entity.SysDept;

import java.util.List;

/**
 * @author Felix
 */
public interface SysDeptMapper {

    SysDept selectByPrimaryKey(String id);

    List<SysDept> selectAll();

    List<String> selectChildIds(String relationCode);

    List<SysDept> selectAllByNotContainChild(List<String> list);

    int insertSelective(SysDept record);

    int updateByPrimaryKeySelective(SysDept record);

    int updateRelationCode(@Param("oldStr") String oldStr, @Param("newStr") String newStr, @Param("relationCode") String relationCode);
}
