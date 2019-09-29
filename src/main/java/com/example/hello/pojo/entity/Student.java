package com.example.hello.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户模型")
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 6680748226543595200L;

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "邮箱")
    private String email;
}
