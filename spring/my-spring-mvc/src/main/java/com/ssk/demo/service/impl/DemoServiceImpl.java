package com.ssk.demo.service.impl;

import com.ssk.demo.service.IDemoService;
import com.ssk.edu.mvcframework.annotations.MyService;

@MyService("demoService")
public class DemoServiceImpl implements IDemoService {
    @Override
    public String get(String name) {
        System.out.println("service 实现类中的name参数：" + name) ;
        return name;
    }
}
