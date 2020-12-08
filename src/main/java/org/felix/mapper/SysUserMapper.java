package org.felix.mapper;

import org.felix.model.entity.SysUser;
import org.felix.model.ro.SysUserAttac;
import org.felix.model.ro.SysUserPhoto;
import org.felix.model.ro.UserPhotoTemp;
import org.felix.model.vo.req.UserPageReqVO;
import org.felix.model.vo.resp.BbsUserVO;
import org.felix.model.vo.resp.EchartsVO;
import org.felix.model.vo.resp.TableStructureVO;
import org.felix.model.vo.resp.TableVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * SysUserMapper
 */
public interface SysUserMapper {

    void updateUserPoint(@Param(value = "userId") String userId, @Param(value = "point") String point);

    List<TableStructureVO> selectTabelByName(String tableName);

    List<TableVO> selectAllTable();

    SysUser getUserInfoByName(String username);

    SysUser selectByPrimaryKey(String id);

    BbsUserVO getBbsUserInfoByPkId(String id);

    //获取某用户的总发帖数量(只算审核通过后的记录)
    int selectPublishPostCount(String userId);

    //获取某用户的总评论数量(只算审核通过后的记录)
    int selectCommentCount(String userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateBySysUserAttacPrimaryKeySelective(SysUserAttac record);

    List<SysUser> selectAll(UserPageReqVO vo);

    List<SysUser> selectAllByParam(UserPageReqVO vo);

    //查询所有用户
    List<SysUser> selectAllUser();

    //新增用户主表信息
    int insertUser(SysUser record);

    //新增用户附表信息
    int insertUserAttac(SysUserAttac record);

    //新增用户头像信息
    int insertUserPhoto(SysUserPhoto record);

    //删除用户
    int deletedUsers(@org.apache.ibatis.annotations.Param("sysUser") SysUser sysUser, @org.apache.ibatis.annotations.Param("list") List<String> list);

    List<SysUser> selectUserInfoByDeptIds(List<String> deptIds);

    //批量插入测试图像
    void batchInsertUserPhotoTemp(UserPhotoTemp obj);

    //查询所有测试头像
    public List<String> selectAllUserPhotoTemp();

    //根据用户ID修改用户头像
    void updateUserPhotoByUserId(@org.apache.ibatis.annotations.Param("userId") String userId, @org.apache.ibatis.annotations.Param("userPhoto") String userPhoto);

    //根据用户邮箱修改用户登录密码
    void updateUserPasswordByEmail(@org.apache.ibatis.annotations.Param("email") String email, @org.apache.ibatis.annotations.Param("password") String password, @org.apache.ibatis.annotations.Param("salt") String salt);

    //根据用户ID修改用户登录密码
    void updateUserPasswordByUserId(@org.apache.ibatis.annotations.Param("id") String id, @org.apache.ibatis.annotations.Param("password") String password, @org.apache.ibatis.annotations.Param("salt") String salt);

    //统计相关******************************************************开始
    //统计各部门总人数
    List<EchartsVO> selectDeptUserCount();

    //统计各性别总人数
    List<EchartsVO> selectUserSexCount();

    //统计各角色总人数
    List<EchartsVO> selectUserRoleCount();

    //统计各工资段人数
    List<EchartsVO> selectSalaryUserCount();

    //数据看版-用户登录浏览器分析
    List<EchartsVO> loginBrowser();

    //获取用户登录归属地信息
    List<EchartsVO> getIpAddressInfo();
    //统计相关******************************************************结束



}
