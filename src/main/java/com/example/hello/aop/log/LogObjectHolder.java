package com.example.hello.aop.log;

import com.example.hello.aop.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * 写操作日志：临时存储修改之前的对象
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION)
public class LogObjectHolder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object object = null;

    public Object get() {
        return object;
    }

    public void set(Object object) {
        this.object = object;
    }

    public static LogObjectHolder me(){
        LogObjectHolder bean = SpringContextHolder.getBean(LogObjectHolder.class);
        return bean;
    }


}
