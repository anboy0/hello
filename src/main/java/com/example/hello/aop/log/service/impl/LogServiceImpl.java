package com.example.hello.aop.log.service.impl;

import com.example.hello.aop.log.bean.OperateLogBean;
import com.example.hello.aop.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveLog(OperateLogBean logBean, String collectionName) {
        mongoTemplate.save(logBean, collectionName);
    }
}
