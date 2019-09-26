package com.example.hello.aop.log.factory;

import com.example.hello.aop.SpringContextHolder;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * <p>功能描述：常量工厂实现类 </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 杭州凯立通信有限公司</p>
 *
 * @author PF0P65Z6
 * @version 1.0 2018年1月10日 上午10:51:57
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {
    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    @Override
    public String getUserStatusById(Integer id) {
        if (1 == id) {
            return "正常";
        } else {
            return "失效";
        }
    }
}
