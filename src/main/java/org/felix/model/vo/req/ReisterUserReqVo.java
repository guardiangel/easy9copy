package org.felix.model.vo.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Felix
 */
public class ReisterUserReqVo implements Serializable {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "登录账号不能为空")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String checkPassword;

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "邮箱验证码")
    @NotBlank(message = "注册验证码不能为空")
    private String emailCode;

    @ApiModelProperty(value = "性别(1：男；2：女；)")
    @NotBlank(message = "性别不能为空")
    private String sex;

    @ApiModelProperty(value = "图片验证码")
    @NotBlank(message = "图片验证码不能为空")
    private String code;

    @ApiModelProperty(value = "创建来源(1.web;2.android;3.ios;)")
    private String createWhere;

    @Override
    public String toString() {
        return "ReisterUserReqVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", checkPassword='" + checkPassword + '\'' +
                ", email='" + email + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", sex='" + sex + '\'' +
                ", code='" + code + '\'' +
                ", createWhere='" + createWhere + '\'' +
                '}';
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

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateWhere() {
        return createWhere;
    }

    public void setCreateWhere(String createWhere) {
        this.createWhere = createWhere;
    }
}
