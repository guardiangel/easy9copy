package org.felix.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.felix.model.vo.resp.PermissionRespNode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Felix
 */
public class SysRole implements Serializable {

    private String id;

    private String name;

    private String description;

    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "", hidden = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间", example = "", hidden = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private List<PermissionRespNode> permissionRespNodes;

    @Override
    public String toString() {
        return "SysRole{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", permissionRespNodes=" + permissionRespNodes +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<PermissionRespNode> getPermissionRespNodes() {
        return permissionRespNodes;
    }

    public void setPermissionRespNodes(List<PermissionRespNode> permissionRespNodes) {
        this.permissionRespNodes = permissionRespNodes;
    }
}
