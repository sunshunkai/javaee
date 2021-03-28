package com.ssk.test;

import com.ssk.io.Resources;
import com.ssk.pojo.User;
import com.ssk.sqlsession.SqlSession;
import com.ssk.sqlsession.SqlSessionFactory;
import com.ssk.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class ITest {

    @Test
    public void test() throws PropertyVetoException, DocumentException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(1L);
        User u = sqlSession.selectOne("user.selectOne", user);
    }
}
