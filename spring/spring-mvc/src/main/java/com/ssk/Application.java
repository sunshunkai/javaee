package com.ssk;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.ssk.lombak.UserBean;
import com.ssk.loop.A;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author ssk
 * @date 2020/8/20
 */
//@EnableAsync
//@EnableScheduling
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableAspectJAutoProxy
@EnableMethodCache(basePackages = {"com.ssk"})
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class Application  {

    public static void main(String []args){
        SpringApplication.run(Application.class);
//        System.out.println(a() || b());

//        UserBean ssk = UserBean.builder().userName("ssk").age(1).id(1L).build();
//        System.out.println(ssk);

//        ApplicationContext context = new AnnotationConfigApplicationContext(A.class);
//
//        context.getEnvironment();
    }

    private static boolean a(){
        return false;
    }

    private static boolean b(){
        return true;
    }


}
