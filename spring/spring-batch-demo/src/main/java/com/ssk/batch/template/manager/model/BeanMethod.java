package com.ssk.batch.template.manager.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Hugh
 * @date 2018/08/15
 */
@Slf4j
@AllArgsConstructor
public class BeanMethod {
    private Object bean;
    private Method method;

    public Object invoke(Object... param) {
        try {
            return method.invoke(bean, Arrays.copyOf(param,method.getParameterCount()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
