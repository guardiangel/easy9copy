package org.felix.model.vo.resp;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Felix
 */
public class EchartsRespVO {

    @ApiModelProperty(value = "名称")
    private List<String> name;

    @ApiModelProperty(value = "数量")
    private List<EchartsVO> count;

    @Override
    public String toString() {
        return "EchartsRespVO{" +
                "name=" + name +
                ", count=" + count +
                '}';
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<EchartsVO> getCount() {
        return count;
    }

    public void setCount(List<EchartsVO> count) {
        this.count = count;
    }
}
