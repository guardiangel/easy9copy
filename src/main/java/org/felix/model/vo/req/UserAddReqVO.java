package org.felix.model.vo.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Felix
 */
public class UserAddReqVO implements Serializable {
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "登录账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "登录密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
    private Integer createWhere;

    @ApiModelProperty(value = "所属部门")
    @NotBlank(message = "所属部门不能为空")
    private String deptId;

    @ApiModelProperty(value = "性别(1.男 2.女)")
    private String sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "账户状态（1：正常；2：锁定；）")
    private Integer status;

    @ApiModelProperty(value = "月薪")
    private BigDecimal monthSalary;

    @ApiModelProperty(value = "积分")
    private String point;

    @ApiModelProperty(value = "所拥有的角色")
    private List<String> roleIds;

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

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "测试头像")
    private String userPhotoTemp;

    @Override
    public String toString() {
        return "UserAddReqVO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createWhere=" + createWhere +
                ", deptId='" + deptId + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", status=" + status +
                ", monthSalary=" + monthSalary +
                ", point='" + point + '\'' +
                ", roleIds=" + roleIds +
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
                ", position='" + position + '\'' +
                ", userPhotoTemp='" + userPhotoTemp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreateWhere() {
        return createWhere;
    }

    public void setCreateWhere(Integer createWhere) {
        this.createWhere = createWhere;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(BigDecimal monthSalary) {
        this.monthSalary = monthSalary;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserPhotoTemp() {
        return userPhotoTemp;
    }

    public void setUserPhotoTemp(String userPhotoTemp) {
        this.userPhotoTemp = userPhotoTemp;
    }
}