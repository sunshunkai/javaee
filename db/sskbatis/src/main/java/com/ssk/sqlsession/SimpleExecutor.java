package com.ssk.sqlsession;

import com.ssk.config.BoundSql;
import com.ssk.pojo.Configuration;
import com.ssk.pojo.MappedStatement;
import com.ssk.utils.GenericTokenParser;
import com.ssk.utils.ParameterMapping;
import com.ssk.utils.ParameterMappingTokenHandler;
import com.ssk.utils.TokenHandler;
import lombok.val;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class SimpleExecutor implements Executor{

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        // 1:注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2: 获取SQL语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBound(sql);

        // 3:获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4:设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> paramterTypeClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String context = parameterMapping.getContext();

            // 反射
            Field declaredField = paramterTypeClass.getDeclaredField(context);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);
        }
        // 5:执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);


        ArrayList<Object> objects = new ArrayList<>();
        // 6:封装返回对象
        while (resultSet.next()){
            Object o = resultTypeClass.newInstance();
            // 元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i=1;i<=metaData.getColumnCount();i++){
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object object = resultSet.getObject(columnName);
                // 使用反射，根据数据库表和实体关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, object);
            }
            objects.add(o);
        }

        return (List<T>) objects;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if(paramterType!=null){
            return Class.forName(paramterType);
        }
        return null;
    }

    /**
     * 完成对#{}的解析：1、将#{}使用？进行代替；2、解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBound(String sql) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", handler);
        // 解析过后的sql
        String parseSql = genericTokenParser.parse(sql);
        // #{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = handler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
        return boundSql;
    }
}
