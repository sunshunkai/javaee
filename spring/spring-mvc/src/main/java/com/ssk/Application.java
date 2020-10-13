package com.ssk;

import com.ssk.lombak.UserBean;
import com.ssk.loop.A;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author ssk
 * @date 2020/8/20
 */
//@EnableAsync
//@EnableScheduling
@SpringBootApplication
public class Application implements ApplicationContextAware {

    public static void main(String []args){
        SpringApplication.run(Application.class);
//        System.out.println(a() || b());

//        UserBean ssk = UserBean.builder().userName("ssk").age(1).id(1L).build();
//        System.out.println(ssk);

        ApplicationContext context = new AnnotationConfigApplicationContext(A.class);

        context.getEnvironment();
    }

    private static boolean a(){
        return false;
    }

    private static boolean b(){
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
