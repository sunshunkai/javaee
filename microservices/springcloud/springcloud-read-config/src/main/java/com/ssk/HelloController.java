package com.ssk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ssk
 * @date 2020/10/19
 */
@RestController
public class HelloController {

    @Value("${cs.name}")
    String name;
    @Value("${cs.age}")
    String age;

    @GetMapping(value = "/hi")
    public String hi(){
        return "我的名字是:"+name+",年龄是:"+age;
    }

}
