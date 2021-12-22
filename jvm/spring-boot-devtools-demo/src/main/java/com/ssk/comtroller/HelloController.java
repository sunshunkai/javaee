package com.ssk.comtroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 惊云
 * @date 2021/12/18 9:00
 */
@RestController
public class HelloController {

    @RequestMapping("devtool-hello")
    public String hello(){
        return "spring-boot-devtools-demo";
    }
}
