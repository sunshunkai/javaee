package com.ssk.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ssk
 * @date 2020/8/20
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "hello spring boot";
    }
}
