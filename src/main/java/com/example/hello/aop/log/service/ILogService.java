package com.example.hello.aop.log.service;

import com.example.hello.aop.log.bean.OperateLogBean;

public interface ILogService {

    //写操作日志
    public void saveLog(OperateLogBean logBean, String collectionName);
}
