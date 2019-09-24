package com.example.hello.aop.log.enums;

/**
 * 操作类型枚举类
 */
public enum OperateType {
    ADD("log.add","新增"),
    UPDATE("log.update","修改"),
    DELETE("log.delete","删除");

    String code;
    String message;

    OperateType(String code, String message) {
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
