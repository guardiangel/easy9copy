package org.felix.model.ro;

import io.swagger.annotations.ApiModelProperty;
import org.felix.model.dto.EmailNoticeDto;

/**
 * @author Felix
 */
public class EmailRO {
    @ApiModelProperty(value = "接收者邮箱", example = "", hidden = false)
    private String to;

    @ApiModelProperty(value = "主题", example = "", hidden = false)
    private String object;

    @ApiModelProperty(value = "邮件内容", example = "", hidden = false)
    private String content;

    @ApiModelProperty(value = "邮件对象", example = "", hidden = false)
    private EmailNoticeDto dto;

    @Override
    public String toString() {
        return "EmailRO{" +
                "to='" + to + '\'' +
                ", object='" + object + '\'' +
                ", content='" + content + '\'' +
                ", dto=" + dto +
                '}';
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmailNoticeDto getDto() {
        return dto;
    }

    public void setDto(EmailNoticeDto dto) {
        this.dto = dto;
    }
}
