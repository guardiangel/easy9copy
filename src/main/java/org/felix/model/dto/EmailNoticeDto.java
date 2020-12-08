package org.felix.model.dto;

/**
 * 发通知给管理员的内容
 * @author Felix
 */
public class EmailNoticeDto {

    /**
     用户姓名
     */
    private String userName;

    /**登录IP*/
    private String loginIp;

    /**登录IP地址*/
    private String loginAddress;

    /**登录时间*/
    private String loginTime;

    /**事件描述*/
    private String eventDesc;

    @Override
    public String toString() {
        return "EmailNoticeDto{" +
                "userName='" + userName + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginAddress='" + loginAddress + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                '}';
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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
