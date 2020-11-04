package com.ssk.loop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ssk
 * @date 2020/9/17
 */
@Configuration
@ComponentScan
public class A {

    @Value("${spring.redis.host:1}")
    private int db;

    public A(){

    }



    @PostConstruct
    public void aa(){
        System.out.println(db);
    }

    @Autowired
    private B b;

}
