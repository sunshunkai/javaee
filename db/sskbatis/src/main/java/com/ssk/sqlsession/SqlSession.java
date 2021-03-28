package com.ssk.sqlsession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/28
 */
public interface SqlSession {

    <T> T selectOne(String statementId,Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    <T> List<T> selectList(String statementId,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException;

    <T> T getMapper(Class<?> mapperClass);

}
