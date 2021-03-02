package com.ssk.batch.itemread.db;

import com.ssk.batch.Customer;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ssk
 * @date 2021/2/28
 */
@Component("dbJdbcWriter")
public class DbJdbcWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
