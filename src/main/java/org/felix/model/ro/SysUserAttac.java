package org.felix.model.ro;

import io.swagger.annotations.ApiModelProperty;

public class SysUserAttac {
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

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

    @Override
    public String toString() {
        return "SysUserAttac{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
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
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
