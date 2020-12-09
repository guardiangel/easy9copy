package org.felix.service;

import org.felix.model.vo.req.UserRoleOperationReqVO;

import java.util.List;

/**
 * @author Felix
 */
public interface UserRoleService {

    List<String> getRolesIdByUserId(String userId);

    int removeByUserId(String userId);

    void addUserRoleInfo(UserRoleOperationReqVO vo);

    int removeByRoleId(String roleId);

    List<String> getUserIdsByRoleId(String roleId);

}
