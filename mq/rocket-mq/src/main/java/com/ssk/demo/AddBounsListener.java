package com.ssk.demo;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ssk
 * @date 2020/8/27
 */
@Component
@RocketMQMessageListener(consumerGroup = "springboot-rocketmq-group", topic = "add-bonus")
public class AddBounsListener implements RocketMQListener<TestMessaging> {

    @Override
    public void onMessage(TestMessaging testMessaging) {
        System.out.println("消费者收到消息:" + testMessaging);
    }
}
