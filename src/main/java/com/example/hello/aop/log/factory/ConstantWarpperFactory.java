package com.example.hello.aop.log.factory;

import java.lang.reflect.Method;

/**
 * <p>功能描述：常量工厂创建类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 杭州凯立通信有限公司</p>
 *
 * @author PF0P65Z6
 * @version 1.0 2018年1月10日 上午10:54:39
 */
public class ConstantWarpperFactory {
    /**
     * 常量工厂创建
     *
     * @param obj
     * @param methodName
     * @return
     */
    public static Object create(Object obj, String methodName) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, obj.getClass());
            Object result = method.invoke(me, obj);
            return result;
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(obj.toString()));
                return result;
            } catch (Exception e1) {
            }
        }

        return me;
    }
}
