package org.felix.service.impl;

import org.felix.aop.constants.Constant;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysDeptMapper;
import org.felix.mapper.SysLoginLogMapper;
import org.felix.mapper.SysUserMapper;
import org.felix.model.entity.*;
import org.felix.model.vo.req.*;
import org.felix.model.vo.resp.*;
import org.felix.service.*;
import org.felix.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Felix
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private TokenSettings tokenSettings;

    @Resource
    private IPAddressTool ipAddressTool;

    @Resource
    private ThirdTool thirdTool;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysLoginLogMapper sysLoginLogMapper;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

    @Resource
    private RedisService redisService;
    @Resource
    private MailService mailService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 是否调用百度接口
     */
    @Value("${system.getIPAddress}")
    private String getIPAddress;


    @Override
    public List<TableStructureVO> selectTabelByName(String tableName) {

        return sysUserMapper.selectTabelByName(tableName);
    }

    @Override
    public List<TableVO> selectAllTable() {

        return sysUserMapper.selectAllTable();
    }

    @Override
    public List<SysUser> getAllUserInfo() {

        return sysUserMapper.selectAllUser();
    }

    @Override
    public List<DeptUserTree> getAllDeptUserTree() {

        boolean nodeCheckedStatus = false;
        List<DeptUserTree> result = new ArrayList<>();
        DeptUserTree treeRoot = new DeptUserTree();
        treeRoot.setId("0");
        treeRoot.setIcon("/images/topTree.png");
        treeRoot.setName("根节点");
        treeRoot.setChecked(nodeCheckedStatus);
        treeRoot.setPid("000");
        //类型（1：员工；2：部门；）
        treeRoot.setType(2);
        result.add(treeRoot);

        //查询部门数据
        List<SysDept> deptList = sysDeptMapper.selectAll();
        if (deptList != null && !deptList.isEmpty()) {
            deptList.forEach(sysDept -> {
                DeptUserTree tree = new DeptUserTree();
                tree.setId(sysDept.getId());
                tree.setIcon("/images/deptTree.png");
                tree.setName(sysDept.getName());
                tree.setChecked(nodeCheckedStatus);
                //类型（1：员工；2：部门；）
                tree.setType(2);
                tree.setPid(sysDept.getPid());
                result.add(tree);
            });
        }

        //查询所有用户数据
        List<SysUser> userList = sysUserMapper.selectAllUser();
        if (userList != null && !userList.isEmpty()) {
            userList.forEach(sysUser -> {
                DeptUserTree tree = new DeptUserTree();
                tree.setId(sysUser.getId());
                tree.setIcon("/images/userPhotoTree.png");
                tree.setName(sysUser.getRealName());
                tree.setChecked(nodeCheckedStatus);
                tree.setType(1);//类型（1：员工；2：部门；）
                tree.setPid(sysUser.getDeptId());
                tree.setMonthSalary(sysUser.getMonthSalary());//月薪
                result.add(tree);
            });

        }

        return result;
    }

    @Override
    public LoginRespVO login(LoginReqVO vo, HttpServletRequest request) {

        SysUser sysUser = sysUserMapper.getUserInfoByName(vo.getUsername());
        if (sysUser == null) {
            throw new ServiceException(BaseResponseCode.NOT_ACCOUNT);
        }
        if (sysUser.getStatus() == 2) {
            throw new ServiceException(BaseResponseCode.USER_LOCK);
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), vo.getPassword(), sysUser.getPassword())) {
            throw new ServiceException(BaseResponseCode.PASSWORD_ERROR);
        }
        LoginRespVO respVO = new LoginRespVO();
        BeanUtils.copyProperties(sysUser, respVO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_PERMISSIONS_KEY, getPermissionsByUserId(sysUser.getId()));
        claims.put(Constant.JWT_ROLES_KEY, getRolesByUserId(sysUser.getId()));
        claims.put(Constant.JWT_USER_NAME, sysUser.getRealName());
        String accessToken = JwtTokenUtil.getAccessToken(sysUser.getId(), claims);
        String refresh_token;
        if (vo.getType().equals("1")) {
            refresh_token = JwtTokenUtil.getRefreshToken(sysUser.getId(), claims);
        } else {
            refresh_token = JwtTokenUtil.getRefreshAppToken(sysUser.getId(), claims);
        }

        respVO.setAccessToken(accessToken);
        respVO.setRefreshToken(refresh_token);
        String ipAddress = "";
        String ip = IPUtils.getIpAddr(request);
        if ("1".equals(getIPAddress)) {
            //是否调用百度API接口获取IP归属地（0：不调用；1：调用）
            ipAddress = ipAddressTool.getAddressById(ip);
        }
        log.info("用户[{}]登录系统成功！", vo.getUsername());
        //发通知邮件给管理员，应该是个异步动作
        //thirdTool.sendNoticeEmail(vo.getUsername(), "成功登录[Easy9云平台]", "[Easy9云平台]有人登录系统", request);
        //**********添加登录日志**********开始//
        SysLoginLog obj = new SysLoginLog();
        obj.setId(Tool.getPrimaryKey());
        obj.setUserId(sysUser.getId());
        obj.setCreateTime(new Date());
        obj.setLoginWay(Integer.valueOf(IPUtils.getAccessType(request)));
        obj.setLoginIp(ip);
        obj.setLoginAddress(ipAddress);
        obj.setLoginBrowser(Tool.getBrowserInfo(request));
        obj.setUserAgent(IPUtils.getUserAgent(request));
        log.info("登录日志：{}", obj.toString());
        sysLoginLogMapper.insertSelective(obj);
        //**********添加登录日志**********结束//
        respVO.setIpAddress(ipAddress);
        return respVO;

    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);

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
