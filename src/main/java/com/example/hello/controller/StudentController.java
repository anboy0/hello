package com.example.hello.controller;

import com.example.hello.aop.OperateLogAspect;
import com.example.hello.aop.log.enums.FunctionName;
import com.example.hello.aop.log.enums.OperateType;
import com.example.hello.aop.log.service.impl.RedisService;
import com.example.hello.mapper.StudentMapper;
import com.example.hello.pojo.entity.Student;
import com.example.hello.pojo.entity.StudentDict;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiVersion(1)
@Api(description = "学生接口")
@RequestMapping("/{version}")
@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private RedisService redisService;

    @GetMapping("/student")
    @ApiOperation(value = "查询所有学生信息")
    public List<Student> queryAll(){

        List<Student> list = studentMapper.selectList(null);
        return  list;
    }

    @GetMapping("/student/{id}")
    @ApiOperation(value = "根据ID查询学生信息")
    public Student queryById(@PathVariable Integer id){

        Student student = studentMapper.selectById(id);
        redisService.set("student",student);
        return  student;
    }

    @PutMapping("/student")
    @ApiOperation(value = "修改学生信息")
    @OperateLogAspect(functionName = FunctionName.studentManage,operateType = OperateType.UPDATE,key = "name",dict = StudentDict.class)
    public Student updateById(@RequestBody Student student){

        studentMapper.updateById(student);
        student = studentMapper.selectById(student.getId());
        return  student;
    }

}
