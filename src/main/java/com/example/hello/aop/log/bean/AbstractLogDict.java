package com.example.hello.aop.log.bean;


import java.util.HashMap;

/**
 * 日志字典抽象类
 */
public abstract class AbstractLogDict {

    //字段和字段名称的映射关系
    protected HashMap<String, String> filedNameMap = new HashMap<>();

    //字段和获取字段值的方法的映射
    protected HashMap<String, String> methodNameMap = new HashMap<>();

    public AbstractLogDict() {
        initFiledNameMap();
        initMethodNameMap();
    }

    //初始化 字段和字段名称的映射关系
    protected abstract void initFiledNameMap();

    //初始化 方法和方法名称的映射关系
    protected abstract void initMethodNameMap();


    public String getFieldName(String key) {
        return this.filedNameMap.get(key);
    }

    public void putFieldName(String key, String value) {
        this.filedNameMap.put(key, value);
    }

    public String getMethodName(String key) {
        return this.methodNameMap.get(key);
    }

    public void putMethodName(String key, String value) {
        this.methodNameMap.put(key, value);
    }
}
