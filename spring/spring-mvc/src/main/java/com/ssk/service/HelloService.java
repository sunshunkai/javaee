package com.ssk.service;

import com.ssk.web.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ssk
 * @date 2020/12/15
 */
@Service
public class HelloService {

    public HelloService(){
        System.out.println("hello");
    }
    @Autowired
    private HelloController helloController;
}
