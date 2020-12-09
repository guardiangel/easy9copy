package org.felix.service;

import org.felix.model.entity.SysPermission;
import org.felix.model.vo.req.PermissionAddReqVO;
import org.felix.model.vo.req.PermissionPageReqVO;
import org.felix.model.vo.req.PermissionUpdateReqVO;
import org.felix.model.vo.resp.PageVO;
import org.felix.model.vo.resp.PermissionRespNode;

import java.util.List;
import java.util.Set;

/**
 * @author Felix
 */
public interface PermissionService {

    /**
     * 根据用户ID，查询菜单
     * @param userId
     * @return
     */
    List<SysPermission> getPermission(String userId);

    /**
     * 根据用户ID， 查询不重复的菜单。
     * @param userId
     * @return
     */
    Set<String> getPermissionsByUserId(String userId);

    /**
     * 以树型的形式把用户拥有的菜单权限返回给客户端
     * @param userId
     * @return
     */
    List<PermissionRespNode> permissionTreeList(String userId);

    /**
     *获取所有菜单权限按钮
     * @return
     */
    List<PermissionRespNode> selectAllByTree();

    /**
     *获得所有菜单权限
     * @return
     */
    List<SysPermission> selectAll();

    /**
     * 增加权限
     * @param vo
     * @return
     */
    SysPermission addPermission(PermissionAddReqVO vo);

    /**
     *删除菜单权限
     * @param permissionId
     */
    void delete(String permissionId);

    /**
     *查询菜单详情
     * @param permissionId
     * @return
     */
    SysPermission detailInfo(String permissionId);

    /**
     *分页查询所有菜单权限
     * @param vo
     * @return
     */
    PageVO<SysPermission> pageInfo(PermissionPageReqVO vo);

    /**
     *根据菜单ID，查询树型菜单权限
     * @param permissionId
     * @return
     */
    List<PermissionRespNode> selectAllMenuByTree(String permissionId);

    /**
     * 更新菜单信息
     * @param vo
     */
    void updatePermission(PermissionUpdateReqVO vo);




}
