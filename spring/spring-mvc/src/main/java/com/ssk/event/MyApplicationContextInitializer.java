package com.ssk.event;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 需要配置：context.initializer.classes: com.ssk.event.MyApplicationContextInitializer
 * @author ssk
 * @date 2020/11/4
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("Spring容器属性之前");
    }
}
