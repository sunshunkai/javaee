package com.ssk.test.dao;

import com.ssk.dao.OrderDao;
import com.ssk.dao.UserDao;
import com.ssk.mode.OrderDO;
import com.ssk.mode.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ssk
 * @date 2020/8/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Test
    public void testMapper(){
        OrderDO order = new OrderDO();
        order.setOrderId(11L);
        order.setAmount(200L);
        orderDao.insert(order);
    }
    
    @Test
    public void test(){
        UserDO user = userDao.findById(1L);

        test1();


        user.setUserName("111111");
        int update = userDao.update(user);
        System.out.println(update);
        System.out.println(user);

//        user.setUserName("22222222");
//
//
//        int update1 = userDao.update(user);
//        System.out.println(update1);
//        System.out.println(user);
    }


    public void test1(){
        UserDO user = userDao.findById(1L);
        user.setUserName("111111");
        int update = userDao.update(user);
        System.out.println(update);
        System.out.println(user);

    }


}
