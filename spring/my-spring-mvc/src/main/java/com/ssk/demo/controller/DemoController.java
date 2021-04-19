package com.ssk.demo.controller;

import com.ssk.demo.service.IDemoService;
import com.ssk.edu.mvcframework.annotations.MyAutowired;
import com.ssk.edu.mvcframework.annotations.MyController;
import com.ssk.edu.mvcframework.annotations.MyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
@MyRequestMapping("/demo")
public class DemoController {


    @MyAutowired
    private IDemoService demoService;


    /**
     * URL: /demo/query?name=lisi
     * @param request
     * @param response
     * @param name
     * @return
     */
    @MyRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response,String name) {
        return demoService.get(name);
    }
}
