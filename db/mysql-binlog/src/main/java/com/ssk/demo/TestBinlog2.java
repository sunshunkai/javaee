package com.ssk.demo;

import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 惊云
 * @date 2021/12/10 15:15
 * @see https://github.com/dothetrick/binlogportal
 */
@Slf4j
public class TestBinlog2 {

    private static final SyncConfig syncConfig;

    private static IPositionHandler positionHandler = new DefaultPositionHandler();

    /**
     * 项目启动读取的配置
     */
    static {
        syncConfig = new SyncConfig();

        syncConfig.setHost("localhost");
        syncConfig.setPort(3306);
        syncConfig.setUserName("root");
        syncConfig.setPassword("123456");
    }

    public static void main(String[] args) throws IOException {

        BinaryLogClient client = new BinaryLogClient(syncConfig.getHost(), syncConfig.getPort(), syncConfig.getUserName(), syncConfig.getPassword());
        EventDeserializer eventDeserializer = new EventDeserializer();
        //时间反序列化的格式
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );
        client.setEventDeserializer(eventDeserializer);
        //设置serverId，不同的集群，机器的serverId不能相同。
        client.setServerId(getRandomServerId());
        //获取position的位置（创建client时，读取当前记录的postion）
        BinlogPositionEntity binlogPositionEntity = positionHandler.getPosition(syncConfig);
        if (binlogPositionEntity != null &&
                binlogPositionEntity.getBinlogName() != null &&
                binlogPositionEntity.getPosition() != null) {
            client.setBinlogFilename(binlogPositionEntity.getBinlogName());
            client.setBinlogPosition(binlogPositionEntity.getPosition());
        }

        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {

                EventHeader header = event.getHeader();

                EventType eventType = header.getEventType();
                System.out.println("监听的事件类型:" + eventType);

                /*
                 * 不计入position更新的事件类型
                 * FORMAT_DESCRIPTION类型为binlog起始时间
                 * HEARTBEAT为心跳检测事件，不会写入master的binlog，记录该事件的position会导致重启时报错
                 */
                List<EventType> excludePositionEventType = new ArrayList<>();
                excludePositionEventType.add(EventType.FORMAT_DESCRIPTION);
                excludePositionEventType.add(EventType.HEARTBEAT);
                if (!excludePositionEventType.contains(eventType)) {
                    BinlogPositionEntity binlogPositionEntity = new BinlogPositionEntity();
                    //处理rotate事件,这里会替换调binlog fileName
                    if (event.getHeader().getEventType().equals(EventType.ROTATE)) {
                        RotateEventData rotateEventData = (RotateEventData) event.getData();
                        binlogPositionEntity.setBinlogName(rotateEventData.getBinlogFilename());
                        binlogPositionEntity.setPosition(rotateEventData.getBinlogPosition());
                        binlogPositionEntity.setServerId(event.getHeader().getServerId());
                    } else { //统一处理事件对应的binlog position
                        //在Redis中获取获取binlog的position配置
                        binlogPositionEntity = positionHandler.getPosition(syncConfig);
                        EventHeaderV4 eventHeaderV4 = (EventHeaderV4) event.getHeader();
                        binlogPositionEntity.setPosition(eventHeaderV4.getPosition());
                        binlogPositionEntity.setServerId(event.getHeader().getServerId());
                    }
                    //将最新的配置保存到Redis中
                    log.info("保存的数据{}", JSON.toJSONString(binlogPositionEntity));
                    positionHandler.savePosition(syncConfig, binlogPositionEntity);

                }
            }
        });
        client.connect();
    }

    private static long getRandomServerId() {
        try {
            return SecureRandom.getInstanceStrong().nextLong();
        } catch (NoSuchAlgorithmException e) {
            return RandomUtils.nextLong();
        }
    }


}
