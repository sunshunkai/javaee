package com.ssk.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/2/3
 */
@Component
public class MyBean {

    @Value("#{${item.search.sort:{index_item: 'value21', key2: 'value2'}}}")
    private Map<String,String> sortFiled;

    @PostConstruct
    public void init(){
        System.out.println(sortFiled.get("index_item"));
    }
}
