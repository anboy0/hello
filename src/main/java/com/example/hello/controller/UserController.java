package com.example.hello.controller;

import com.example.hello.pojo.entity.User;
import com.example.hello.pojo.entity.mycollection;
import com.example.hello.service.UserMongoService;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@ApiVersion(5)
@RestController
@Api(description = "用户接口")
@RequestMapping("/{version}")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserMongoService userMongoService;

    @GetMapping("/user")
    @ApiOperation(value = "查询所有User")
    public mycollection queryUser(@RequestParam String name){
        logger.info("log4j2打印日志：查询所有mongodb中用户");
        mycollection mycollection = userMongoService.getUserbyName(name);
        return mycollection;
    }

    @PostMapping("/user")
    @ApiOperation(value = "保存User")
    public void saveUser(@RequestBody mycollection mycollection){
        logger.info("log4j2打印日志：保存mongodb用户");
        userMongoService.savemyCollection(mycollection);
    }
}
