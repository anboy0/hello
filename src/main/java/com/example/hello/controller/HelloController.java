package com.example.hello.controller;

import com.example.hello.mapper.UserMapper;
import com.example.hello.pojo.entity.User;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ApiVersion(5)
@RestController
@Api(description = "用户接口")
@RequestMapping("/{version}")
public class HelloController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    @ApiOperation(value = "版本5")
    @ResponseBody
    public String helloFive(HttpServletRequest request){
        String uri = request.getRequestURI().toString();
        String url = request.getRequestURL().toString();
        System.out.println(uri);
        System.out.println(url);
        System.out.println("版本5");
        return  "版本5";
    }

    @GetMapping("/hello")
    @ApiOperation(value = "版本7")
    @ResponseBody
    @ApiVersion(7)
    public String helloSeven(HttpServletRequest request){
        String uri = request.getRequestURI().toString();
        String url = request.getRequestURL().toString();
        System.out.println(uri);
        System.out.println(url);
        System.out.println("版本7");
        return  "版本7";
    }


    @GetMapping("test")
    @ApiOperation(value = "查询所有User")
    public void queryUser(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
