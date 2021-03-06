package com.example.hello.aop.log.enums;


/**
 * 功能名称枚举类
 */
public enum FunctionName {

    userManage("userManage", "用户管理"),
    studentManage("studentManage", "学生管理");

    String code;
    String message;

    FunctionName(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage(String code) {
        for (FunctionName c : FunctionName.values()) {
            if (c.getCode() == code) {
                return c.message;
            }
        }
        return null;
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
