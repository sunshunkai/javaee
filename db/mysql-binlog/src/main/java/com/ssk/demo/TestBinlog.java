package com.ssk.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author 惊云
 * @date 2021/12/10 15:09
 * @see https://www.jianshu.com/p/a9dbd3fd52f3
 */
@Slf4j
public class TestBinlog {

    private static final ParserConfig snakeCase;

    static {
        snakeCase = new ParserConfig();
        snakeCase.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    public static void main(String[] args) throws IOException {

        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "123456");
        EventDeserializer eventDeserializer = new EventDeserializer();
        //时间反序列化的格式
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );
        client.setEventDeserializer(eventDeserializer);

        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {

                EventHeader header = event.getHeader();

                EventType eventType = header.getEventType();
                System.out.println("监听的事件类型:" + eventType);

                if (EventType.isWrite(eventType)) {
                    //获取事件体
                    WriteRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                } else if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                } else if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = event.getData();
                    log.info(JSON.toJSONString(data));
                }
            }
        });
        client.connect();
    }

}
