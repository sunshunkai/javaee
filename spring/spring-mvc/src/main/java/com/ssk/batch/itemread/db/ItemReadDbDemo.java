package com.ssk.batch.itemread.db;

import com.ssk.batch.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/2/28
 */
@Configuration
public class ItemReadDbDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ItemWriter<? super Customer> dbJdbcWriter;

    @Bean
    public Job itemReaderDbJob(){
        return jobBuilderFactory.get("itemReaderDbJob")
                .start(itemReaderDbStep()).build();
    }


    @Bean
    public Step itemReaderDbStep(){
        return stepBuilderFactory.get("itemReaderDbStep").<Customer,Customer>chunk(2)
                .reader(dbJdbcReader()).writer(dbJdbcWriter).build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Customer> dbJdbcReader(){
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);

        // 把读取到的记录转换成对象
        reader.setRowMapper(new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setBirthday(resultSet.getDate(4));
                return customer;
            }
        });

        // 指定查询sql
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,first_name,last_name,birthday");
        provider.setFromClause("customer");

        // 根据那个字段排序
        Map<String, Order> sort = new HashMap<>();
        sort.put("id",Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }
}
