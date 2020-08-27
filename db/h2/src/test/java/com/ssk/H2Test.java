package com.ssk;

import com.ssk.dao.UserDao;
import com.ssk.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author ssk
 * @date 2020/8/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class H2Test {

    @Autowired
    private UserDao userDao;

    @Test
    public void save(){
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setPhone("123123");
        userDao.save(user);

        System.out.println(userDao.findByName("张三"));
    }

    @Test
    public void testGetUserByName(){
        User user= userDao.findByName("张三");
        System.out.println("查询到用户名为："+user.getName()+"的电话为："+user.getPhone());
    }

    @Test
    public void testGetUserAll(){
        Iterable<User>userIterable=userDao.findAll();
        for(User user:userIterable){
            System.out.println("查询到用户名为："+user.getName()+"的电话为："+user.getPhone());
        }
    }



}
