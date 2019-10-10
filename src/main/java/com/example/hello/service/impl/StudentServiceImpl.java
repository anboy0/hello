package com.example.hello.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hello.mapper.StudentMapper;
import com.example.hello.pojo.entity.Student;
import com.example.hello.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    @Async
    public Student queryStudentById(Long id) {
        logger.info("StudentServiceImpl=====>"+Thread.currentThread().getName());
        return baseMapper.selectById(id);
    }

    @Async
    public Future<Student> queryStudentById2(Long id) {
        logger.info("StudentServiceImpl==queryStudentById2===>"+Thread.currentThread().getName());
        Student student = baseMapper.selectById(id);
        return new AsyncResult<>(student);
    }
}

