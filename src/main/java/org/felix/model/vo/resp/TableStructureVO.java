package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 数据库表结构
 * @author Felix
 */
public class TableStructureVO implements Serializable {

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "列名")
    private String columnName;

    @ApiModelProperty(value = "是否可为空")
    private String isNullable;

    @ApiModelProperty(value = "数据类型")
    private String columnType;

    @ApiModelProperty(value = "数据长度")
    private String characterMaximumLength;

    @ApiModelProperty(value = "列注释")
    private String columnComment;

    @Override
    public String toString() {
        return "TableStructureVO{" +
                "tableName='" + tableName + '\'' +
                ", columnName='" + columnName + '\'' +
                ", isNullable='" + isNullable + '\'' +
                ", columnType='" + columnType + '\'' +
                ", characterMaximumLength='" + characterMaximumLength + '\'' +
                ", columnComment='" + columnComment + '\'' +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(String characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
