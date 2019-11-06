package com.example.hello.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hello.pojo.entity.Student;

import java.util.List;
import java.util.concurrent.Future;

public interface StudentService extends IService<Student> {

    Student queryStudentById(Long id);

    Future<Student> queryStudentById2(Long id);

    List<Student> queryStudent();
}
