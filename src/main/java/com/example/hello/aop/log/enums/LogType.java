package com.example.hello.aop.log.enums;

/**
 * 日志的业务类型
 */
public enum LogType {
    LOGIN("login", "登录日志"),
    EXCEPTION("exception", "异常日志"),
    OPERATION("operation", "业务日志");

    String code;
    String message;


    LogType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
