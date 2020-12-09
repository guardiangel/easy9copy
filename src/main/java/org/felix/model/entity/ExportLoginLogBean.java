package org.felix.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Felix
 */
@ContentRowHeight(25)
@HeadRowHeight(36)
@ColumnWidth(38)
public class ExportLoginLogBean implements Serializable {

    @ExcelProperty(value = {"用户ID"}, index = 0)
    private String userId;

    @ExcelProperty(value = {"登录时间"}, index = 1)
    private Date createTime;

    @ExcelProperty(value = {"登录账号"}, index = 2)
    private String userName;

    @ExcelProperty(value = {"登录IP"}, index = 3)
    private String loginIp;

    @ExcelProperty(value = {"登录地点"}, index = 4)
    private String loginAddress;

    @ExcelProperty(value = {"登录浏览器"}, index = 5)
    private String loginBrowser;

    @ExcelProperty(value = {"登录渠道(1：Web；2：H5；3：Android；4：IOS；5：其他；)"}, index = 6)
    private Integer loginWay;

    @Override
    public String toString() {
        return "ExportLoginLogBean{" +
                "userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", userName='" + userName + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginAddress='" + loginAddress + '\'' +
                ", loginBrowser='" + loginBrowser + '\'' +
                ", loginWay=" + loginWay +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
