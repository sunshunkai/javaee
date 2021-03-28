package com.ssk.sqlsession;

import com.ssk.pojo.Configuration;
import com.ssk.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, ClassNotFoundException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<T> objects = selectList(statementId, params);
        if(objects.size() ==1){
            return objects.get(0);
        }
        throw new RuntimeException("查询结果过多");
    }

    @Override
    public <T> List<T> selectList(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, NoSuchFieldException, SQLException, InvocationTargetException, ClassNotFoundException {
        // 将要去完成对simpleExecutor里的query方法调用
        Executor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<T> query = executor.query(configuration, mappedStatement, params);
        return query;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK代理为Dao生产代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[mapperClass], new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层还是去执行JDBC代码，根据不同情况调用selectList或者selectOne
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statsmentId = className + "." + className;


                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了泛型类型参数化
                if(genericReturnType instanceof  ParameterizedType){
                    List<Object> objects = selectList(statsmentId,args);
                    return objects;
                }
                return selectOne(statsmentId,args);
            }
        });
        return (T)proxyInstance;
    }
}
