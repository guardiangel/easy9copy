package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表信息
 *
 * @author Felix
 */
public class TableVO implements Serializable {

    @ApiModelProperty(value = "schema")
    private String tableSchema;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "总行数")
    private Integer tableRows;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "表注释")
    private String tableComment;

    @Override
    public String toString() {
        return "TableVO{" +
                "tableSchema='" + tableSchema + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableRows=" + tableRows +
                ", createTime=" + createTime +
                ", tableComment='" + tableComment + '\'' +
                '}';
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTableRows() {
        return tableRows;
    }

    public void setTableRows(Integer tableRows) {
        this.tableRows = tableRows;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
