package com.bbu.shenma.handle;

/**
 * 自定义异常
 */
public class EduException extends RuntimeException {

    private Integer code;
    private String message;

    public EduException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public EduException(){}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
