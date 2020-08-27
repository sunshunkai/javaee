package com.ssk.test.dao;

import com.ssk.dao.UserDao;
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
    
    @Test
    public void test(){
        userDao.findById(1L)
    }
}
