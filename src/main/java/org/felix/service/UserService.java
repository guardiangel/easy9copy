package org.felix.service;

import org.felix.model.entity.DeptUserTree;
import org.felix.model.entity.SysUser;
import org.felix.model.entity.UserTree;
import org.felix.model.vo.req.*;
import org.felix.model.vo.resp.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Felix
 */
public interface UserService {

    List<TableStructureVO> selectTabelByName(String tableName);

    List<TableVO> selectAllTable();

    /**
     *查询所有用户信息
     * @return
     */
    List<SysUser> getAllUserInfo();

    /**
     *获取所有部门用户树型数据
     * @return
     */
    List<DeptUserTree> getAllDeptUserTree();

    /**
     *用户登录
     * @param vo
     * @param request
     * @return
     */
    LoginRespVO login(LoginReqVO vo, HttpServletRequest request);

    /**
     *用户登录BBS
     * @param vo
     * @return
     */
    LoginRespVO bbsLogin(LoginReqVO vo);

    /**
     *用户退出
     * @param accessToken
     * @param refreshToken
     */
    public void logout(String accessToken, String refreshToken);

    /**
     *根据ID,查询用户详情
     * @param userId
     * @return
     */
    SysUser detailInfo(String userId);

    /**
     *根据用户ID查询BBS用户详情
     * @param userId
     * @return
     */
    BbsUserVO getBbsUserInfoByPkId(String userId);

    /**
     * 更新用户信息
     * @param vo
     * @param operationId
     */
    public void updateUserInfo(UserUpdateReqVO vo, String operationId);

    /**
     *用户修改自己个人信息
     * @param vo
     * @param operationId
     */
    public void updateUserInfoSelf(UserUpdateReqVO vo, String operationId);

    /**
     *用户修改自己个人信息
     * @param vo
     * @param userId
     */
    public void updateBbsUserInfoSelf(BbsUserUpdateReqVO vo, String userId);

    /**
     * 根据用户邮箱修改用户密码
     * @param email
     * @param password
     */
    public void updateUserPasswordByEmail(String email, String password);

    /**
     * 根据用户ID修改用户密码
     * @param userId
     * @param password
     */
    public void updateUserPasswordByUserId(String userId, String password);

    /**
     *设置用户角色
     * @param userId
     * @param roleIds
     */
    public void setUserOwnRole(String userId, List<String> roleIds);

    /**
     *查询用户分页信息
     * @param vo
     * @return
     */
    public PageVO<SysUser> pageInfo(UserPageReqVO vo);

    /**
     * 刷新TOKEN
     * @param refreshToken
     * @param accessToken
     * @return
     */
    public String refreshToken(String refreshToken, String accessToken);

    /**
     * 注册用户
     * @param vo
     * @param request
     */
    public void registerUser(ReisterUserReqVo vo, HttpServletRequest request);

    /**
     * 新增用户
     * @param vo
     * @param userId
     */
    public void addUser(UserAddReqVO vo, String userId);

    /**
     * 删除用户
     * @param userIds
     * @param userId
     */
    public void deleteUsers(List<String> userIds, String userId);

    public UserOwnRoleRespVO getUserOwnRole(String userId);

    public PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize, List<String> deptIds);

    public List<SysUser> getUserListByDeptIds(List<String> deptIds);

    /**
     * 查询用户头像
     * @return
     */
    public List<String> selectAllUserPhotoTemp();

    /**
     *根据用户ID修改头像
     * @param userId
     * @param userPhoto
     */
    public void updateUserPhotoByUserId(String userId, String userPhoto);

    /**
     *获取用户部门树型数据
     * @return
     */
    public List<UserTree> selectAllByTree();

    /**
     *获取用户相关统计图数据
     * @param type
     * @return
     */
    public EchartsRespVO userEcharts(String type);

    /**
     * 数据看版-用户登录浏览器分析
     * @return
     */
    public EchartsRespVO loginBrowser();



}