package com.example.hello.aop.mybatis;


import com.example.hello.aop.log.enums.FunctionName;
import com.example.hello.aop.log.enums.OperateType;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperateLogAspect {

    //功能名称
    FunctionName functionName();

    //操作类型
    OperateType operateType();

    //业务操作实体的主键
    String key() default "id";


}
