package com.example.hello.pojo.entity;

import com.example.hello.aop.log.bean.AbstractLogDict;

public class StudentDict extends AbstractLogDict {

    @Override
    protected void initFiledNameMap() {
        putFieldName("id", "主键ID");
        putFieldName("name", "名称");
        putFieldName("age", "年龄");
        putFieldName("email", "邮箱");
    }

    @Override
    protected void initMethodNameMap() {

    }
}
