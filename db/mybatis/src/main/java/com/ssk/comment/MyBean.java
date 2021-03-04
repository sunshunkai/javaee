package com.ssk.comment;

import com.ssk.dao.UserDao;
import com.ssk.mode.UserDO;
import com.ssk.sql.SqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/2/3
 */
@Component
public class MyBean {

    @Value("#{${item.search.sort:{index_item: 'value21', key2: 'value2'}}}")
    private Map<String,String> sortFiled;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SqlMapper sqlMapper;

    @PostConstruct
    public void init(){
        System.out.println(sortFiled.get("index_item"));
        UserDO user = userDao.findById(1L);
        System.out.println(user);
        List<Map<String, Object>> maps = sqlMapper.selectList("select * from user");
        System.out.println(maps);

    }
}
