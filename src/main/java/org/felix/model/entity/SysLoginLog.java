package org.felix.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Felix
 */
public class SysLoginLog implements Serializable {

    @ApiModelProperty(value = "主键ID", example = "", hidden = false)
    private String id;

    @ApiModelProperty(value = "操作时间", example = "", hidden = false)
    private Date createTime;

    @ApiModelProperty(value = "登录用户ID", example = "", hidden = false)
    private String userId;

    @ApiModelProperty(value = "登录IP地址", example = "", hidden = false)
    private String loginIp;

    @ApiModelProperty(value = "登录IP所属地", example = "", hidden = false)
    private String loginAddress;

    @ApiModelProperty(value = "登录浏览器", example = "", hidden = false)
    private String loginBrowser;

    @ApiModelProperty(value = "登录方式（1：Web；2：H5；3：Android；4：IOS；；5：其他；）", example = "", hidden = false)
    private Integer loginWay;

    @ApiModelProperty(value = "请求request得User_Agent")
    private String userAgent;

    @Override
    public String toString() {
        return "SysLoginLog{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginAddress='" + loginAddress + '\'' +
                ", loginBrowser='" + loginBrowser + '\'' +
                ", loginWay=" + loginWay +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getLoginBrowser() {
        return loginBrowser;
    }

    public void setLoginBrowser(String loginBrowser) {
        this.loginBrowser = loginBrowser;
    }

    public Integer getLoginWay() {
        return loginWay;
    }

    public void setLoginWay(Integer loginWay) {
        this.loginWay = loginWay;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
