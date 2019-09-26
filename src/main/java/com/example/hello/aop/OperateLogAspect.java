package com.example.hello.aop;


import com.example.hello.aop.log.bean.AbstractLogDict;
import com.example.hello.aop.log.bean.DefaultLogDict;
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

    //字段和字段名称的映射 字段和方法名称的映射
    Class<? extends AbstractLogDict> dict() default DefaultLogDict.class;
}
