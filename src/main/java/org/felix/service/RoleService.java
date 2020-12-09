package org.felix.service;

import org.felix.model.entity.SysRole;
import org.felix.model.vo.req.RoleAddReqVO;
import org.felix.model.vo.req.RolePageReqVO;
import org.felix.model.vo.req.RoleUpdateReqVO;
import org.felix.model.vo.resp.PageVO;

import java.util.List;

/**
 * @author Felix
 */
public interface RoleService {
     List<String> getRoleNames(String userId);

     List<SysRole> getRoleInfoByUserId(String userId);

    List<SysRole> selectAllRoles();

    /**
     * 分页查询角色列表
     * @param vo
     * @return
     */
     PageVO<SysRole> pageInfo(RolePageReqVO vo);

    /**
     * 根据ID删除角色
     * @param id
     */
     void deletedRole(String id);

    /**
     * 根据ID查询角色详情
     * @param id
     * @return
     */
     SysRole detailInfo(String id);

    /**
     * 新增角色
     * @param vo
     * @return
     */
     SysRole addRole(RoleAddReqVO vo);

    /**
     * 更新角色信息
     * @param vo
     * @param accessToken
     */
     void updateRole(RoleUpdateReqVO vo, String accessToken);
}
