package com.ssk.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/19
 */
@Configuration(proxyBeanMethods = false)
//@ConditionalOnClass(EnableRabbit.class)   <optional>true</optional> jar不会传递
@ConditionalOnClass(name = "")
public class MyAnnotationDrivenConfiguration {

}

