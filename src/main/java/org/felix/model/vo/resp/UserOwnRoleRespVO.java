package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import org.felix.model.entity.SysRole;

import java.util.List;

/**
 * 用户拥有的角色
 * @author Felix
 */
public class UserOwnRoleRespVO {

    @ApiModelProperty(value = "所有角色集合")
    private List<SysRole> allRole;

    @ApiModelProperty(value = "用户所拥有角色集合")
    private List<String> ownRoles;

    @Override
    public String toString() {
        return "UserOwnRoleRespVO{" +
                "allRole=" + allRole +
                ", ownRoles=" + ownRoles +
                '}';
    }

    public List<SysRole> getAllRole() {
        return allRole;
    }

    public void setAllRole(List<SysRole> allRole) {
        this.allRole = allRole;
    }

    public List<String> getOwnRoles() {
        return ownRoles;
    }

    public void setOwnRoles(List<String> ownRoles) {
        this.ownRoles = ownRoles;
    }
}
