package com.ssk.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.mapperhelper.MapperInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author ssk
 * @date 2020/8/21
 */
@Configuration
public class DataSourceConfig {

//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3308/test");
//        dataSource.setUsername("root");
//        dataSource.setPassword("ssk123456");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        return dataSource;
//    }

    /**
     * tk.mybatis插件
     * @return
     */
    @Bean
    public MapperInterceptor mapperInterceptor(){
        MapperInterceptor interceptor = new MapperInterceptor();
        Properties properties = new Properties();
        properties.setProperty("mappers","tk.mybatis.mapper.common.Mapper");
        interceptor.setProperties(properties);
        return interceptor;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }


    /**
     * 注册MyBatis插件
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {

        return new ConfigurationCustomizer() {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {

                MapperInterceptor interceptor = new MapperInterceptor();
                Properties properties = new Properties();
                properties.setProperty("mappers","tk.mybatis.mapper.common.Mapper");
                interceptor.setProperties(properties);
                configuration.addInterceptor(interceptor);

            }
        };

    }











}
