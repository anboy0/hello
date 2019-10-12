package com.example.hello.aop;

import com.example.hello.aop.log.bean.OperateLogBean;
import com.example.hello.aop.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线程异步写日志任务
 */
@Component
public class LogAsyncTask {

    @Autowired
    private ILogService logService;

    @Async("logExecutor")
    public void saveLog(OperateLogBean logBean, String collectionName) {
        logService.saveLog(logBean, collectionName);
    }
}
