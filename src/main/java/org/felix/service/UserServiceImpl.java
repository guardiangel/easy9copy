package org.felix.service;

import org.felix.model.entity.DeptUserTree;
import org.felix.model.entity.SysUser;
import org.felix.model.entity.UserTree;
import org.felix.model.vo.req.*;
import org.felix.model.vo.resp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<TableStructureVO> selectTabelByName(String tableName) {
        return null;
    }

    @Override
    public List<TableVO> selectAllTable() {
        return null;
    }

    @Override
    public List<SysUser> getAllUserInfo() {
        return null;
    }

    @Override
    public List<DeptUserTree> getAllDeptUserTree() {
        return null;
    }

    @Override
    public LoginRespVO login(LoginReqVO vo, HttpServletRequest request) {
        return null;
    }

    @Override
    public LoginRespVO bbsLogin(LoginReqVO vo) {
        return null;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {

    }

    @Override
    public SysUser detailInfo(String userId) {
        return null;
    }

    @Override
    public BbsUserVO getBbsUserInfoByPkId(String userId) {
        return null;
    }

    @Override
    public void updateUserInfo(UserUpdateReqVO vo, String operationId) {

    }

    @Override
    public void updateUserInfoSelf(UserUpdateReqVO vo, String operationId) {

    }

    @Override
    public void updateBbsUserInfoSelf(BbsUserUpdateReqVO vo, String userId) {

    }

    @Override
    public void updateUserPasswordByEmail(String email, String password) {

    }

    @Override
    public void updateUserPasswordByUserId(String userId, String password) {

    }

    @Override
    public void setUserOwnRole(String userId, List<String> roleIds) {

    }

    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        return null;
    }

    @Override
    public String refreshToken(String refreshToken, String accessToken) {
        return null;
    }

    @Override
    public void registerUser(ReisterUserReqVo vo, HttpServletRequest request) {

    }

    @Override
    public void addUser(UserAddReqVO vo, String userId) {

    }

    @Override
    public void deleteUsers(List<String> userIds, String userId) {

    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        return null;
    }

    @Override
    public PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize, List<String> deptIds) {
        return null;
    }

    @Override
    public List<SysUser> getUserListByDeptIds(List<String> deptIds) {
        return null;
    }

    @Override
    public List<String> selectAllUserPhotoTemp() {
        return null;
    }

    @Override
    public void updateUserPhotoByUserId(String userId, String userPhoto) {

    }

    @Override
    public List<UserTree> selectAllByTree() {
        return null;
    }

    @Override
    public EchartsRespVO userEcharts(String type) {
        return null;
    }

    @Override
    public EchartsRespVO loginBrowser() {
        return null;
    }
}
