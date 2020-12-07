package org.felix.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Felix
 */
public class SysUser implements Serializable {

    @ApiModelProperty(value = "用户主键")
    private String id;

    @ApiModelProperty(value = "登录帐号")
    private String username;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "部门ID")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

    private String statusStr;

    @ApiModelProperty(value = "性别(1：男；2：女；)")
    private Integer sex;

    private String sexStr;

    @ApiModelProperty(value = "是否删除(1未删除；0已删除)")
    private Integer deleted;

    @ApiModelProperty(value = "创建人ID")
    private String createId;

    @ApiModelProperty(value = "更新人ID")
    private String updateId;

    @ApiModelProperty(value = "创建人姓名")
    private String createUserName;

    @ApiModelProperty(value = "更新人姓名")
    private String updateUserName;

    @ApiModelProperty(value = "创建来源(1.web;2.android;3.ios;)")
    private Integer createWhere;

    private String createWhereStr;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "积分")
    private String point;

    @ApiModelProperty(value = "用户月薪")
    private BigDecimal monthSalary;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "职位描述")
    private String positionStr;

    //用户附表信息
    @ApiModelProperty(value = "工资卡号")
    private String wagesNumber;

    @ApiModelProperty(value = "社保卡号")
    private String socialNumber;

    @ApiModelProperty(value = "公积金卡号")
    private String providentFundNumber;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "国籍")
    private String country;

    @ApiModelProperty(value = "家庭住址")
    private String homeAddress;

    @ApiModelProperty(value = "居住地址")
    private String liveAddress;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "毕业院校")
    private String graduationSchool;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "政治面貌")
    private String identity;

    @ApiModelProperty(value = "身高(CM)")
    private String height;

    @ApiModelProperty(value = "体重(KG)")
    private String weight;

    @ApiModelProperty(value = "血型")
    private String bloodType;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "qq")
    private String qq;

    @ApiModelProperty(value = "微信号")
    private String webchat;

    @ApiModelProperty(value = "婚姻状态")
    private String marriage;

    @ApiModelProperty(value = "MSN账号")
    private String msn;

    @ApiModelProperty(value = "是否服军役(0:否;1:是;)")
    private String militaryService;

    @ApiModelProperty(value = "用户头像base64数据")
    private String photo;

    @Override
    public String toString() {
        return "SysUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", statusStr='" + statusStr + '\'' +
                ", sex=" + sex +
                ", sexStr='" + sexStr + '\'' +
                ", deleted=" + deleted +
                ", createId='" + createId + '\'' +
                ", updateId='" + updateId + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                ", createWhere=" + createWhere +
                ", createWhereStr='" + createWhereStr + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", point='" + point + '\'' +
                ", monthSalary=" + monthSalary +
                ", position='" + position + '\'' +
                ", positionStr='" + positionStr + '\'' +
                ", wagesNumber='" + wagesNumber + '\'' +
                ", socialNumber='" + socialNumber + '\'' +
                ", providentFundNumber='" + providentFundNumber + '\'' +
                ", nation='" + nation + '\'' +
                ", country='" + country + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", liveAddress='" + liveAddress + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", graduationSchool='" + graduationSchool + '\'' +
                ", education='" + education + '\'' +
                ", identity='" + identity + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", major='" + major + '\'' +
                ", qq='" + qq + '\'' +
                ", webchat='" + webchat + '\'' +
                ", marriage='" + marriage + '\'' +
                ", msn='" + msn + '\'' +
                ", militaryService='" + militaryService + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Integer getCreateWhere() {
        return createWhere;
    }

    public void setCreateWhere(Integer createWhere) {
        this.createWhere = createWhere;
    }

    public String getCreateWhereStr() {
        return createWhereStr;
    }

    public void setCreateWhereStr(String createWhereStr) {
        this.createWhereStr = createWhereStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public BigDecimal getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(BigDecimal monthSalary) {
        this.monthSalary = monthSalary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
    }

    public String getWagesNumber() {
        return wagesNumber;
    }

    public void setWagesNumber(String wagesNumber) {
        this.wagesNumber = wagesNumber;
    }

    public String getSocialNumber() {
        return socialNumber;
    }

    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }

    public String getProvidentFundNumber() {
        return providentFundNumber;
    }

    public void setProvidentFundNumber(String providentFundNumber) {
        this.providentFundNumber = providentFundNumber;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGraduationSchool() {
        return graduationSchool;
    }

    public void setGraduationSchool(String graduationSchool) {
        this.graduationSchool = graduationSchool;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getMilitaryService() {
        return militaryService;
    }

    public void setMilitaryService(String militaryService) {
        this.militaryService = militaryService;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
