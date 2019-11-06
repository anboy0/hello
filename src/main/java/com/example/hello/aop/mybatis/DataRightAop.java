package com.example.hello.aop.mybatis;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限切点
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface DataRightAop {
    String value() default "";
}
