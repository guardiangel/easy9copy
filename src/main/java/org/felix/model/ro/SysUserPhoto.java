package org.felix.model.ro;

import io.swagger.annotations.ApiModelProperty;

/**
 *系统用户头像
 * @author Felix
 */
public class SysUserPhoto {

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户头像base64数据")
    private String photo;

    @Override
    public String toString() {
        return "SysUserPhoto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", photo='" + photo + '\'' +
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
