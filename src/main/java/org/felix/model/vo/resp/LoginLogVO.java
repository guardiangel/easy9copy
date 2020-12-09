package org.felix.model.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Felix
 */
public class LoginLogVO implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 登录用户ID
     */
    private String userId;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String loginIp;

    /**
     * 登录地
     */
    private String loginAddress;

    /**
     * 登录浏览器
     */
    private String loginBrowser;

    /**
     * 登录方式（1：Web；2：H5；3：Android；4：IOS；；5：其他；）
     */
    private Integer loginWay;

    @Override
    public String toString() {
        return "LoginLogVO{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginAddress='" + loginAddress + '\'' +
                ", loginBrowser='" + loginBrowser + '\'' +
                ", loginWay=" + loginWay +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
