package com.example.hello.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobDemoService implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("JobDemoService 执行了，执行时间为："+ new Date());
    }
}

