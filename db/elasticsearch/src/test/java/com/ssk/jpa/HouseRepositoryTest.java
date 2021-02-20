package com.ssk.jpa;

import com.ssk.jpa.mode.HouseIndexTemplate;
import com.ssk.jpa.mode.dao.HouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ssk
 * @date 2020/12/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseRepositoryTest {

    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void selectUser(){
        HouseIndexTemplate template = new HouseIndexTemplate();
        template.setId(1L);
        template.setName("Tom");
        houseRepository.save(template);
    }


}













