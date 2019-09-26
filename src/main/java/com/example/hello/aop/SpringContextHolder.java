package com.example.hello.aop;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring上下文工具类
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        assertApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String beanName){
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType){
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static Map<String,Object> getBeans(Class type){
        return applicationContext.getBeansOfType(type);
    }

    public static List<String> getBeansName(Class type){
        Map<String,Object> beans =  applicationContext.getBeansOfType(type);
        List<String> ret = new ArrayList<>();
        for (Map.Entry<String,Object> entry: beans.entrySet()) {
            ret.add(entry.getValue().getClass().getName());
        }
        return ret;
    }

    private static void assertApplicationContext(){
        if(SpringContextHolder.applicationContext == null){
            throw new RuntimeException("applicationContext属性为null,请检查是否注入了SpringContextHolder");
        }
    }
}
