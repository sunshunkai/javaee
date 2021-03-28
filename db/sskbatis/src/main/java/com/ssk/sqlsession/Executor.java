package com.ssk.sqlsession;

import com.ssk.pojo.Configuration;
import com.ssk.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/28
 */
public interface Executor {

    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement,Object...params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

}
