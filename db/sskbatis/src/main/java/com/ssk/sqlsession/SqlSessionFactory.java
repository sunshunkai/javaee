package com.ssk.sqlsession;

/**
 * @author ssk
 * @date 2021/3/28
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
