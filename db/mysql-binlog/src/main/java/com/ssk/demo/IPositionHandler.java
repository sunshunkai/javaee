package com.ssk.demo;

/**
 * 处理binlog位点信息接口，实现该接口创建自定义位点处理类
 * @author 惊云
 * @date 2021/12/10 15:12
 */
public interface IPositionHandler {

    BinlogPositionEntity getPosition(SyncConfig syncConfig);

    void savePosition(SyncConfig syncConfig, BinlogPositionEntity binlogPositionEntity);

}
