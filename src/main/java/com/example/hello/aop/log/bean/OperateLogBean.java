package com.example.hello.aop.log.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 操作日志Bean
 */
@Data
@Document(collection = "t_operate_log")
public class OperateLogBean {

    //主键Id
    @Id
    private String id;
    //key
    private String key;
    //功能名称
    private String functionName;
    //操作类型
    private String operateType;
    //创建人
    private String createByName;
    //创建时间
    private Date createTime;
    // 日志类型
    private String logType;
    //日志明细
    private String msg;
    //描述
    private String describe;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;

}
