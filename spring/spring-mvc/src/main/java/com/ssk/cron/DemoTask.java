package com.ssk.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2020/9/10
 */
@Component
public class DemoTask {

//    @Scheduled(cron = "0/1 * * * * ?")
    public void test(){
        System.out.println("=======================");
    }
    
}
