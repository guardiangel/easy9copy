package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Felix
 */
public class EchartsVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数量")
    private String value;

    @Override
    public String toString() {
        return "EchartsVO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
