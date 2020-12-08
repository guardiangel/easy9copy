package org.felix.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 部门人员树型结构
 *
 * @author Felix
 */
public class DeptUserTree {

    @ApiModelProperty(value = "节点ID")
    private String id;

    @ApiModelProperty(value = "父节点")
    private String pid;

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "节点是否选中（true：是，fale: 否）")
    private boolean checked;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "类型（1 员工， 2 部门）")
    private Integer type;

    @ApiModelProperty(value = "薪水")
    private BigDecimal monthSalary;

    @Override
    public String toString() {
        return "DeptUserTree{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", checked=" + checked +
                ", icon='" + icon + '\'' +
                ", type=" + type +
                ", monthSalary=" + monthSalary +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public BigDecimal getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(BigDecimal monthSalary) {
        this.monthSalary = monthSalary;
    }
}
