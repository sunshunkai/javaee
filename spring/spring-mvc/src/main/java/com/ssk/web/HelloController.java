package com.ssk.web;

import com.ssk.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ssk
 * @date 2020/8/20
 */
@RestController
@RequestMapping("he")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("hello")
    public String hello(){
        return helloService.hello("ssk");
    }

    @RequestMapping("hello1")
    public String hello1(){
        return helloService.hello("ssk");
    }

//    @Component
    @RestController
    class OutClass{
        public OutClass(){
            System.out.println("内部OutClass初始化");
        }

        @RequestMapping("hello-1")
        public String out(){
            hello();
           return helloService.hello("张三");
        }
    }
}
