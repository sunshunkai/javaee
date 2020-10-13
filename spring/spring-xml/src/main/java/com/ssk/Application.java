package com.ssk;

import com.ssk.bean.Student;
import com.ssk.mylabel.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ssk
 * @date 2020/8/24
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        Student bean = context.getBean(Student.class);
//        System.out.println(bean);
        User user = (User) context.getBean("testBean");
        System.out.println(user);

    }
}
