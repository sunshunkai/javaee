package com.ssk.demo;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的端点续传存储器（需要借助分布式存储——此处可以保存到Redis中）
 *
 * @author 惊云
 * @date 2021/12/10 15:13
 */
public class DefaultPositionHandler implements IPositionHandler{

    private Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public BinlogPositionEntity getPosition(SyncConfig syncConfig) {
        return JSON.parseObject(cache.get(generateKey(syncConfig)), BinlogPositionEntity.class);
    }

    @Override
    public void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity) {
        cache.put(generateKey(syncConfig), JSON.toJSONString(binlogPositionEntity));
    }


    /**
     * @param syncConfig 参数配置
     * @return 生成的key
     */
    private String generateKey(SyncConfig syncConfig) {
        return syncConfig.getHost() + ":" + syncConfig.getPort();
    }

}
