package com.example.hello.controller;

import com.example.hello.pojo.entity.Student;
import com.example.hello.service.StudentService;
import com.example.hello.version.ApiVersion;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 线程异步
 */
@ApiVersion(1)
@Api(description = "线程接口")
@RequestMapping("/{version}")
@RestController
public class AsyncController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @GetMapping("/async/{id}")
    public Student queryStudentById(@PathVariable("id") Long id) throws InterruptedException, ExecutionException {
        logger.info("AsyncController======>" + Thread.currentThread().getName());
        Student student = studentService.queryStudentById(id);
        logger.info("AsyncController==student.name====>" + (student != null ? student.getName() : null) + "=====>" + Thread.currentThread().getName());
        logger.info("*******************************************");
        Future<Student> future = studentService.queryStudentById2(id);
        String name = future.get().getName();
        logger.info("AsyncController==future.name====>" + name + "=====>" + Thread.currentThread().getName());
        return student;
    }


}
