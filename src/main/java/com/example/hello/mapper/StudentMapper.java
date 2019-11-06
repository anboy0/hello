package com.example.hello.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hello.aop.mybatis.DataRightAop;
import com.example.hello.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @DataRightAop
    List<Student> queryStudent();

}
