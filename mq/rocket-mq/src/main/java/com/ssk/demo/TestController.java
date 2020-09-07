package com.ssk.demo;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ssk
 * @date 2020/8/27
 */
@RestController
public class TestController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/test")
    public String test(String msg){
        System.out.println("========" + msg);
        TestMessaging testMessaging = new TestMessaging()
                .setMsgId(UUID.randomUUID().toString())
                .setMsgText(msg);
        rocketMQTemplate.convertAndSend("add-bonus", testMessaging);
        return "投递消息 => " + msg + " => 成功";
    }

}
