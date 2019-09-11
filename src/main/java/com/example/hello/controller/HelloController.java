package com.example.hello.controller;

import com.example.hello.mapper.UserMapper;
import com.example.hello.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class HelloController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("hello")
    public String hello(){
        return "第一个方法";
    }

    @RequestMapping("test")
    public void queryUser(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
