package com.ssk.bean;


import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * 将Bean中的方法体替换
 * @author ssk
 * @date 2020/9/12
 */
public class TestMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("我替换了原有的方法");
        return null;
    }
}
