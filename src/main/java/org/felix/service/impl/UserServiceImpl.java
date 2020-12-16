package org.felix.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.felix.aop.constants.Constant;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.mapper.SysDeptMapper;
import org.felix.mapper.SysLoginLogMapper;
import org.felix.mapper.SysUserMapper;
import org.felix.model.entity.*;
import org.felix.model.ro.SysUserAttac;
import org.felix.model.ro.SysUserPhoto;
import org.felix.model.vo.req.*;
import org.felix.model.vo.resp.*;
import org.felix.service.*;
import org.felix.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        SysUser sysUser = sysUserMapper.getUserInfoByName(vo.getUsername());
        if (null == sysUser) {
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
        String access_token = JwtTokenUtil.getAccessToken(sysUser.getId(), claims);
        String refresh_token;
        if (vo.getType().equals("1")) {
            refresh_token = JwtTokenUtil.getRefreshToken(sysUser.getId(), claims);
        } else {
            refresh_token = JwtTokenUtil.getRefreshAppToken(sysUser.getId(), claims);
        }
        respVO.setAccessToken(access_token);
        respVO.setRefreshToken(refresh_token);
        log.info("用户[{}]登录BBS成功！", sysUser.getRealName());

        return respVO;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {

        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String userId = JwtTokenUtil.getUserId(accessToken);
        log.info("用户[{}]退出系统成功！", userId);
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + accessToken, userId,
                JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId,
                JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
        redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);

    }

    @Override
    public SysUser detailInfo(String userId) {

        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        String militaryService = user.getMilitaryService();//是否服军役(0:否;1:是;)
        int createWhere = user.getCreateWhere();//创建来源(1.web;2.android;3.ios;)
        String createWhereStr = "";
        String height = user.getHeight();//身高(CM)
        String weight = user.getWeight();//体重(KG)
        int status = user.getStatus();//账户状态(1.正常 2.锁定 )
        String statusStr = "";
        int sex = user.getSex();//性别(1：男；2：女；)
        String sexStr = "";
        if("0".equals(militaryService)){
            militaryService = "未服军役";
        }else if("1".equals(militaryService)){
            militaryService = "服过军役";
        }
        if(1 == createWhere){
            createWhereStr = "web";
        }else if(2 == createWhere){
            createWhereStr = "android";
        }else if(3 == createWhere){
            createWhereStr = "ios";
        }
        if(1 == status){
            statusStr = "正常状态";
        }else if(2 == status){
            statusStr = "锁定状态";
        }
        if(1 == sex){
            sexStr = "男";
        }else if(2 == sex){
            sexStr = "女";
        }
        user.setMilitaryService(militaryService);
        user.setCreateWhereStr(createWhereStr);
        user.setHeight(height+"CM");
        user.setWeight(weight+"KG");
        user.setStatusStr(statusStr);
        user.setSexStr(sexStr);

        return user;
    }

    @Override
    public BbsUserVO getBbsUserInfoByPkId(String userId) {

        int publishPostCount = sysUserMapper.selectPublishPostCount(userId);
        int commentCount = sysUserMapper.selectCommentCount(userId);
        BbsUserVO bbsUserVO = sysUserMapper.getBbsUserInfoByPkId(userId);
        bbsUserVO.setPublishPostCount(publishPostCount);
        bbsUserVO.setCommentCount(commentCount);
        bbsUserVO.setLevel(LevelTools.getLevelByPoint(bbsUserVO.getPoint()));
        bbsUserVO.setLevelName(LevelTools.getLevelNameByPoint(bbsUserVO.getPoint(), bbsUserVO.getSex()));

        return bbsUserVO;
    }

    @Override
    public void updateUserInfo(UserUpdateReqVO vo, String userId) {
        if(!Tool.isBlank(vo.getEmail())){
            //判断邮箱是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setEmail(vo.getEmail());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(vo.getId())){
                        throw new ServiceException(BaseResponseCode.REGISTER_EXIST);
                    }
                }
            }
        }
        if(!Tool.isBlank(vo.getUsername())){
            //验证登录账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setUsername(vo.getUsername());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(vo.getId())){
                        throw new ServiceException(BaseResponseCode.USERNAME_EXIST);
                    }
                }
            }
        }
        if(!Tool.isBlank(vo.getRealName())){
            //验证真实账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setRealName(vo.getRealName());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(vo.getId())){
                        throw new ServiceException(BaseResponseCode.REALNAME_EXIST);
                    }
                }
            }
        }

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(vo.getId());
        if (null == sysUser) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        sysUser.setUsername(vo.getUsername());
        sysUser.setPhone(vo.getPhone());
        sysUser.setDeptId(vo.getDeptId());
        sysUser.setSex(vo.getSex());
        sysUser.setEmail(vo.getEmail());
        sysUser.setRealName(vo.getRealName());
        sysUser.setNickName(vo.getNickName());
        sysUser.setStatus(vo.getStatus());
        sysUser.setUpdateId(userId);
        sysUser.setUpdateTime(new Date());
        sysUser.setMonthSalary(vo.getMonthSalary());
        sysUser.setPosition(vo.getPosition());
        if (!Tool.isBlank(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword("");
        }
        //更新用户主表信息
        int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //更新用户附表信息
        SysUserAttac userAttac = getData3(vo);
        userAttac.setUserId(sysUser.getId());
        int j = sysUserMapper.updateBySysUserAttacPrimaryKeySelective(userAttac);
        if (j != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        if (vo.getStatus() != null) {
            if (2 == vo.getStatus()) {
                redisService.set(Constant.ACCOUNT_LOCK_KEY + sysUser.getId(), sysUser.getId());
            } else {
                redisService.delete(Constant.ACCOUNT_LOCK_KEY + sysUser.getId());
            }
        }
    }

    private SysUser getData1(UserAddReqVO vo){
        SysUser user = new SysUser();
        user.setUsername(vo.getUsername());
        user.setPhone(vo.getPhone());
        user.setCreateWhere(vo.getCreateWhere());
        user.setDeptId(vo.getDeptId());
        user.setSex(Integer.valueOf(vo.getSex()));
        user.setEmail(vo.getEmail());
        user.setRealName(vo.getRealName());
        user.setNickName(vo.getNickName());
        user.setStatus(vo.getStatus());
        user.setMonthSalary(vo.getMonthSalary());
        user.setPosition(vo.getPosition());
        return user;
    }

    private SysUserAttac getData2(UserAddReqVO vo){
        SysUserAttac user = new SysUserAttac();
        user.setWagesNumber(vo.getWagesNumber());
        user.setSocialNumber(vo.getSocialNumber());
        user.setProvidentFundNumber(vo.getProvidentFundNumber());
        user.setNation(vo.getNation());
        user.setCountry(vo.getCountry());
        user.setHomeAddress(vo.getHomeAddress());
        user.setLiveAddress(vo.getLiveAddress());
        user.setIdNumber(vo.getIdNumber());
        user.setGraduationSchool(vo.getGraduationSchool());
        user.setEducation(vo.getEducation());
        user.setIdentity(vo.getIdentity());
        user.setHeight(vo.getHeight());
        user.setWeight(vo.getWeight());
        user.setBloodType(vo.getBloodType());
        user.setMajor(vo.getMajor());
        user.setQq(vo.getQq());
        user.setWebchat(vo.getWebchat());
        user.setMarriage(vo.getMarriage());
        user.setMsn(vo.getMsn());
        user.setMilitaryService(vo.getMilitaryService());
        return user;
    }

    private SysUserAttac getData3(UserUpdateReqVO vo){
        SysUserAttac user = new SysUserAttac();
        user.setWagesNumber(vo.getWagesNumber());
        user.setSocialNumber(vo.getSocialNumber());
        user.setProvidentFundNumber(vo.getProvidentFundNumber());
        user.setNation(vo.getNation());
        user.setCountry(vo.getCountry());
        user.setHomeAddress(vo.getHomeAddress());
        user.setLiveAddress(vo.getLiveAddress());
        user.setIdNumber(vo.getIdNumber());
        user.setGraduationSchool(vo.getGraduationSchool());
        user.setEducation(vo.getEducation());
        user.setIdentity(vo.getIdentity());
        user.setHeight(vo.getHeight());
        user.setWeight(vo.getWeight());
        user.setBloodType(vo.getBloodType());
        user.setMajor(vo.getMajor());
        user.setQq(vo.getQq());
        user.setWebchat(vo.getWebchat());
        user.setMarriage(vo.getMarriage());
        user.setMsn(vo.getMsn());
        user.setMilitaryService(vo.getMilitaryService());
        return user;
    }

    @Override
    public void updateUserInfoSelf(UserUpdateReqVO vo, String userId) {
        if(!Tool.isBlank(vo.getEmail())){
            //判断邮箱是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setEmail(vo.getEmail());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(userId)){
                        throw new ServiceException(BaseResponseCode.REGISTER_EXIST);
                    }
                }
            }
        }
        if(!Tool.isBlank(vo.getUsername())){
            //验证登录账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setUsername(vo.getUsername());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(userId)){
                        throw new ServiceException(BaseResponseCode.USERNAME_EXIST);
                    }
                }
            }
        }
        if(!Tool.isBlank(vo.getRealName())){
            //验证真实账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setRealName(vo.getRealName());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                for(int i=0; i<list.size(); i++){
                    SysUser u = list.get(i);
                    if(!u.getId().equals(userId)){
                        throw new ServiceException(BaseResponseCode.REALNAME_EXIST);
                    }
                }
            }
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(vo.getId());
        if (null == sysUser) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        sysUser.setUsername(vo.getUsername());
        sysUser.setPhone(vo.getPhone());
        sysUser.setDeptId(vo.getDeptId());
        sysUser.setSex(vo.getSex());
        sysUser.setEmail(vo.getEmail());
        sysUser.setRealName(vo.getRealName());
        sysUser.setNickName(vo.getNickName());
        sysUser.setStatus(vo.getStatus());
        sysUser.setUpdateId(userId);
        sysUser.setUpdateTime(new Date());
        if (!Tool.isBlank(vo.getPassword())) {
            String newPassword = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
            sysUser.setPassword(newPassword);
        } else {
            sysUser.setPassword("");
        }
        //更新用户主表信息
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (i != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //更新用户附表信息
        SysUserAttac userAttac = getData3(vo);
        userAttac.setUserId(sysUser.getId());
        int j = sysUserMapper.updateBySysUserAttacPrimaryKeySelective(userAttac);
        if (j != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public void updateBbsUserInfoSelf(BbsUserUpdateReqVO vo, String userId) {

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(vo.getId());
        if (null == sysUser) {
            throw new ServiceException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (count != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }

    }

    @Override
    public void updateUserPasswordByEmail(String email, String password) {

        String salt = PasswordUtils.getSalt();
        String encode = PasswordUtils.encode(password, salt);
        sysUserMapper.updateUserPasswordByEmail(email, encode, salt);

    }

    @Override
    public void updateUserPasswordByUserId(String userId, String password) {

        String salt = PasswordUtils.getSalt();
        String encode = PasswordUtils.encode(password, salt);
        sysUserMapper.updateUserPasswordByUserId(userId, encode, salt);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setUserOwnRole(String userId, List<String> roleIds) {

        userRoleService.removeByUserId(userId);
        if (null != roleIds && !roleIds.isEmpty()) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(userId);
            reqVO.setRoleIds(roleIds);
            userRoleService.addUserRoleInfo(reqVO);
        }
        redisService.set(Constant.JWT_REFRESH_KEY + userId, userId,
                tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
        //清空权鉴缓存
        redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);

    }

    @Override
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {

        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysUser> sysUsers = sysUserMapper.selectAll(vo);
        return PageUtils.getPageVO(sysUsers);
    }

    @Override
    public String refreshToken(String refreshToken, String accessToken) {

        if (redisService.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST + refreshToken)
                || !JwtTokenUtil.validateToken(refreshToken)) {
            throw new ServiceException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId = JwtTokenUtil.getUserId(refreshToken);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUser) {
            throw new ServiceException(BaseResponseCode.TOKEN_ERROR);
        }
        Map<String, Object> claims = null;
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            claims = new HashMap<>();
            claims.put(Constant.JWT_ROLES_KEY, getRolesByUserId(userId));
            claims.put(Constant.JWT_PERMISSIONS_KEY, getPermissionsByUserId(userId));
        }
        String newAccessToken = JwtTokenUtil.refreshToken(refreshToken, claims);
        redisService.setIfAbsent(Constant.JWT_REFRESH_STATUS + accessToken, userId, 1, TimeUnit.MINUTES);
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            redisService.set(Constant.JWT_REFRESH_IDENTIFICATION + newAccessToken, userId, redisService.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
        }

        return newAccessToken;
    }

    @Override
    public void registerUser(ReisterUserReqVo vo, HttpServletRequest request) {

        SysUser sysUser = new SysUser();
        String userId = Tool.getPrimaryKey();
        sysUser.setId(userId);
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
        sysUser.setUsername(vo.getUsername());//登录账号
        sysUser.setRealName(vo.getUsername());//默认将登录账号作为真实姓名
        sysUser.setNickName(vo.getUsername());//默认将登录账号作为昵称
        sysUser.setPassword(encode);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateId(userId);
        sysUser.setPoint("5");//新注册用户默认给5个积分
        sysUser.setSex(Integer.valueOf(vo.getSex()));
        sysUser.setDeptId("202006301429312201997");//默认值
        sysUser.setEmail(vo.getEmail());
        sysUser.setCreateWhere(Integer.valueOf(vo.getCreateWhere()));
        SysUserAttac userAttac = new SysUserAttac();//用户附表信息
        userAttac.setId(Tool.getPrimaryKey());
        userAttac.setUserId(userId);
        //新增用户主表信息
        int i = sysUserMapper.insertUser(sysUser);
        if (i != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //新增用户附表信息
        userAttac.setId(Tool.getPrimaryKey());
        userAttac.setUserId(userId);
        int j = sysUserMapper.insertUserAttac(userAttac);
        if (j != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //新增用户头像信息
        SysUserPhoto userPhoto = new SysUserPhoto();
        userPhoto.setId(Tool.getPrimaryKey());
        userPhoto.setUserId(userId);
        //性别(1.男 2.女)
        if("1".equalsIgnoreCase(vo.getSex())){
            userPhoto.setPhoto("");
        }
        else if("2".equalsIgnoreCase(vo.getSex())){
            userPhoto.setPhoto("");
        }
        int k = sysUserMapper.insertUserPhoto(userPhoto);
        if (k != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //新增用户角色信息
        UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
        reqVO.setUserId(sysUser.getId());
        List<String> roleList = new ArrayList<>();
        roleList.add("202006301430431602513");//默认值
        reqVO.setRoleIds(roleList);
        userRoleService.addUserRoleInfo(reqVO);
        //注册成功后，向管理员发送告知邮件
        String ip = IPUtils.getIpAddr(request);
        String ip_address = ipAddressTool.getAddressById(ip);
        String content = "注册邮箱为[" + vo.getEmail() + "]的[" + vo.getUsername() + "]用户注册成功，IP：" + ip + "，地点：[" + ip_address + "]";
        //thirdTool.sendNoticeEmail("有人注册", content);
    }

    @Override
    public void addUser(UserAddReqVO vo, String userId) {

        if(!Tool.isBlank(vo.getEmail())){
            //判断邮箱是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setEmail(vo.getEmail());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                throw new ServiceException(BaseResponseCode.REGISTER_EXIST);
            }
        }
        if(!Tool.isBlank(vo.getUsername())){
            //验证登录账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setUsername(vo.getUsername());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                throw new ServiceException(BaseResponseCode.USERNAME_EXIST);
            }
        }
        if(!Tool.isBlank(vo.getRealName())){
            //验证登录账号是否已存在
            UserPageReqVO param = new UserPageReqVO();
            param.setRealName(vo.getRealName());
            List<SysUser> list = sysUserMapper.selectAllByParam(param);
            if(list != null && list.size() != 0){
                throw new ServiceException(BaseResponseCode.REALNAME_EXIST);
            }
        }
        SysUser sysUser = getData1(vo);//用户主表信息
        SysUserAttac userAttac = getData2(vo);//用户附表信息
        String newUserId = Tool.isBlank(vo.getId()) ? Tool.getPrimaryKey(): vo.getId();
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode = PasswordUtils.encode(vo.getPassword(), sysUser.getSalt());
        sysUser.setPassword(encode);
        sysUser.setId(newUserId);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateId(userId);
        if(Tool.isBlank(vo.getPoint())){
            sysUser.setPoint("5");//新增用户默认给5个积分
        }else{
            sysUser.setPoint(vo.getPoint());
        }
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        //新增用户主表信息
        int i = sysUserMapper.insertUser(sysUser);
        if (i != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //新增用户附表信息
        userAttac.setId(Tool.getPrimaryKey());
        userAttac.setUserId(newUserId);
        int j = sysUserMapper.insertUserAttac(userAttac);
        if (j != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        //新增用户头像信息
        SysUserPhoto userPhoto = new SysUserPhoto();
        userPhoto.setId(Tool.getPrimaryKey());
        userPhoto.setUserId(newUserId);
        //性别(1.男 2.女)
        if(Tool.isBlank(vo.getUserPhotoTemp())){
            if("1".equalsIgnoreCase(vo.getSex())){
                userPhoto.setPhoto("");
            }
            else if("2".equalsIgnoreCase(vo.getSex())){
                userPhoto.setPhoto("");
            }
        }else{
            userPhoto.setPhoto(vo.getUserPhotoTemp());
        }
        int k = sysUserMapper.insertUserPhoto(userPhoto);
        if (k != 1) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        if (null != vo.getRoleIds() && !vo.getRoleIds().isEmpty()) {
            UserRoleOperationReqVO reqVO = new UserRoleOperationReqVO();
            reqVO.setUserId(newUserId);
            reqVO.setRoleIds(vo.getRoleIds());
            //新增用户角色关系信息
            userRoleService.addUserRoleInfo(reqVO);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUsers(List<String> userIds, String userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUpdateId(userId);
        sysUser.setUpdateTime(new Date());
        sysUser.setDeleted(0);//0:已删除;1:未删除;
        int i = sysUserMapper.deletedUsers(sysUser, userIds);
        if (i == 0) {
            throw new ServiceException(BaseResponseCode.OPERATION_ERROR);
        }
        for (String uId : userIds) {
            redisService.set(Constant.DELETED_USER_KEY + uId, uId, tokenSettings.getRefreshTokenExpireAppTime().toMillis(), TimeUnit.MILLISECONDS);
            //清空权鉴缓存
            redisService.delete(Constant.IDENTIFY_CACHE_KEY + uId);
        }
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {

        List<String> roleIdsByUserId = userRoleService.getRolesIdByUserId(userId);
        List<SysRole> list = roleService.selectAllRoles();
        UserOwnRoleRespVO vo = new UserOwnRoleRespVO();
        vo.setAllRole(list);
        vo.setOwnRoles(roleIdsByUserId);

        return vo;
    }

    @Override
    public PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize, List<String> deptIds) {

        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.selectUserInfoByDeptIds(deptIds);

        return PageUtils.getPageVO(list);
    }

    @Override
    public List<SysUser> getUserListByDeptIds(List<String> deptIds) {

        return sysUserMapper.selectUserInfoByDeptIds(deptIds);
    }

    @Override
    public List<String> selectAllUserPhotoTemp() {

        return sysUserMapper.selectAllUserPhotoTemp();
    }

    @Override
    public void updateUserPhotoByUserId(String userId, String userPhoto) {

        sysUserMapper.updateUserPhotoByUserId(userId, userPhoto);
    }

    @Override
    public List<UserTree> selectAllByTree() {

        List<UserTree> userTreeList = new ArrayList<UserTree>();
        //查询所有用户
        List<SysUser> userList = sysUserMapper.selectAllUser();
        //查询所有部门
        List<SysDept> deptList = sysDeptMapper.selectAll();
        UserTree userTree = new UserTree();
        return null;
    }

    @Override
    public EchartsRespVO userEcharts(String type) {

        List<EchartsVO> voList = new ArrayList<EchartsVO>();
        //1：查询各部门总人数；2：查询各性别总人数；3：
        if("1".equals(type)){
            voList = sysUserMapper.selectDeptUserCount();
        } else if("2".equals(type)){
            voList = sysUserMapper.selectUserSexCount();
        } else if("3".equals(type)){
            voList = sysUserMapper.selectUserRoleCount();
        } else if("4".equals(type)){
            voList = sysUserMapper.selectSalaryUserCount();
        }
        EchartsRespVO respVO = new EchartsRespVO();
        List<String> nameList = new ArrayList<String>();
        if(CollectionUtils.isNotEmpty(voList)){
            for(int i=0; i<voList.size(); i++){
                EchartsVO vo = voList.get(i);
                nameList.add(Tool.isBlank(vo.getName()) ? "无" : vo.getName());
            }
        }
        respVO.setName(nameList);
        respVO.setCount(voList);

        return respVO;
    }

    @Override
    public EchartsRespVO loginBrowser() {

        List<EchartsVO> voList = sysUserMapper.loginBrowser();
        EchartsRespVO respVO = new EchartsRespVO();
        List<String> nameList = new ArrayList<String>();
        if(CollectionUtils.isNotEmpty(voList)){
            for(int i=0; i<voList.size(); i++){
                EchartsVO vo = voList.get(i);
                nameList.add(Tool.isBlank(vo.getName()) ? "无" : vo.getName());
            }
        }
        respVO.setName(nameList);
        respVO.setCount(voList);

        return respVO;
    }
}
