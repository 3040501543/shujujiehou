package com.quartzJobTask;

import org.springframework.context.annotation.Configuration;

/**
 * @Author xubin
 * @Date 2022/10/8 20:59
 * @Version
 **/

@Configuration
public class QuartzSimpleConfig {
//    //指定具体的定时任务类
//    @Bean
//    public JobDetail uploadTaskDetail() {
//        return JobBuilder.newJob(QuartzSimpleTask.class)
//                .withIdentity("QuartzSimpleTask")
//                .storeDurably().build();
//    }
//
//    @Bean
//    public Trigger uploadTaskTrigger() {
//        //这里设定触发执行的方式
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
//        // 返回任务触发器
//        return TriggerBuilder.newTrigger().forJob(uploadTaskDetail())
//                .withIdentity("QuartzSimpleTask")
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
}