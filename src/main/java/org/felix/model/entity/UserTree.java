package org.felix.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Felix
 */
public class UserTree {

    @ApiModelProperty(value = "节点ID")
    private String id;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "父级名称")
    private String pidName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "节点类型（1：用户；2：部门；）")
    private Integer type;

    @ApiModelProperty(value = "是否选中,默认未选中(false)")
    private boolean checked;

    @ApiModelProperty(value = "是否展开,默认不展开(false)")
    private boolean spread;

    @ApiModelProperty(value = "子节点")
    private List<?> children;

    @Override
    public String toString() {
        return "UserTree{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", pid='" + pid + '\'' +
                ", pidName='" + pidName + '\'' +
                ", icon='" + icon + '\'' +
                ", type=" + type +
                ", checked=" + checked +
                ", spread=" + spread +
                ", children=" + children +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
