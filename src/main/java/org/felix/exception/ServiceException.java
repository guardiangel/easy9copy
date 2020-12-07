package org.felix.exception;

import org.felix.code.ResponseCodeInterface;

/**
 * 通用异常处理类
 *
 * @author Felix
 */
public class ServiceException extends RuntimeException {

    private final int messageCode;

    private final String detailMessage;

    public ServiceException(int messageCode, String detailMessage) {
        this.messageCode = messageCode;
        this.detailMessage = detailMessage;
    }

    public ServiceException(ResponseCodeInterface code) {
        this(code.getCode(), code.getMsg());
    }

    public ServiceException(String message) {
        this(-000001, message);
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
