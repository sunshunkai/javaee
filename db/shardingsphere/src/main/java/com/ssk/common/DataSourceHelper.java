package com.ssk.common;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/1/17
 */
public class DataSourceHelper {


    private static final String HOST = "";
    private static final int PORT = 3306;
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";

    public static DataSource createDataSource(final String dataSourceName){
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUsername("");

        return result;
    }

    private static Map<String,DataSource> createDataSourceMap(){
        Map<String,DataSource> map = new HashMap<>();
        map.put("ds0",createDataSource("ds0"));
        map.put("ds1",createDataSource("ds1"));
        return map;
    }


}
