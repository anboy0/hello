package com.example.hello.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class DemoTaskScheduler {

    public static void main(String[] args) {

        SchedulerFactory factoryBean = new StdSchedulerFactory();

        try {
            Scheduler scheduler = factoryBean.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(JobDemoService.class).withIdentity("myjob1","mygroud1").build();
            String cron = "0/5 36 13 * * ?";
            jobDetail.getJobDataMap().put("cron",cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("mytrigger1","mygroud1").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
