package com.example.hello.aop.log.factory;

/**
 * <p>功能描述：常量工厂接口</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: 杭州凯立通信有限公司</p>
 *
 * @author PF0P65Z6
 * @version 1.0 2018年1月10日 上午10:47:35
 */
public interface IConstantFactory {
    /**
     * 根据用户ID获取用户状态
     *
     * @param id
     * @return
     */
    String getUserStatusById(Integer id);
}
