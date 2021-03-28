package com.ssk.sqlsession;

import com.ssk.pojo.Configuration;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
