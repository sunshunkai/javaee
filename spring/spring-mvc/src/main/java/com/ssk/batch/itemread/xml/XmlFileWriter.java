package com.ssk.batch.itemread.xml;

import com.ssk.batch.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ssk
 * @date 2021/2/28
 */
//@Component("xmlFileWriter")
public class XmlFileWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for(Customer c : list){
            System.out.println(c);
        }
    }
}
