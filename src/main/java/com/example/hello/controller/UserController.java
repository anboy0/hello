package com.example.hello.controller;

import com.example.hello.aop.OperateLogAspect;
import com.example.hello.aop.log.enums.FunctionName;
import com.example.hello.aop.log.enums.OperateType;
import com.example.hello.pojo.entity.mycollection;
import com.example.hello.service.UserMongoService;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;


@ApiVersion(5)
@RestController
@Api(description = "用户接口")
@RequestMapping("/{version}")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserMongoService userMongoService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/user")
    @ApiOperation(value = "查询所有User")
    public mycollection queryUser(@RequestParam String name){
        logger.info("log4j2打印日志：查询所有mongodb中用户");
        mycollection mycollection = userMongoService.getUserbyName(name);
        return mycollection;
    }

    @PostMapping("/user")
    @ApiOperation(value = "保存User")
    @OperateLogAspect(functionName = FunctionName.userManage,operateType = OperateType.ADD,key = "name")
    public void saveUser(@RequestBody mycollection mycollection){
        logger.info("log4j2打印日志：保存mongodb用户");
        userMongoService.savemyCollection(mycollection);
    }

    @PutMapping("/user")
    @ApiOperation(value = "修改User")
    @OperateLogAspect(functionName = FunctionName.userManage,operateType = OperateType.UPDATE,key = "age")
    public void updateUser(@RequestBody mycollection mycollection){
        logger.info("log4j2打印日志：修改mongodb用户");
        Query query = new Query(new Criteria("name").is(mycollection.getName()));
        Update update = new Update();
        update.set("age",mycollection.getAge());
        update.set("email",mycollection.getEmail());
        mongoTemplate.updateFirst(query,update,mycollection.class);
    }
}
