package com.ssk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author ssk
 * @date 2020/8/20
 */
//@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String []args){
        SpringApplication.run(Application.class);
    }

}
