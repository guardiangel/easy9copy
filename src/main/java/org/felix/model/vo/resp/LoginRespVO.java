package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Felix
 */
public class LoginRespVO {

    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户真实姓名")
    private String realName;

    @ApiModelProperty(value = "别名")
    private String nickName;

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "登录IP")
    private String ipAddRess;

    @ApiModelProperty(value = "用户所拥有的菜单权限(前后端分离返回给前端控制菜单和按钮的显示和隐藏)")
    private List<PermissionRespNode> list;

    @Override
    public String toString() {
        return "LoginRespVO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", ipAddRess='" + ipAddRess + '\'' +
                ", list=" + list +
                '}';
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIpAddRess() {
        return ipAddRess;
    }

    public void setIpAddRess(String ipAddRess) {
        this.ipAddRess = ipAddRess;
    }

    public List<PermissionRespNode> getList() {
        return list;
    }

    public void setList(List<PermissionRespNode> list) {
        this.list = list;
    }
}
