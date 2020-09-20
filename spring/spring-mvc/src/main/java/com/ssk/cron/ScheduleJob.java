package com.ssk.cron;

import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2020/9/10
 */
@Component
public class ScheduleJob {

    @CronScheduled(cron = "0/1 * * * * ?",desc = "定时任务")
    public void com(){
        System.out.println("当前时间:" + System.currentTimeMillis());
    }

}
