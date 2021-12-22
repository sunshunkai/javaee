package com.ssk.demo;

import lombok.Data;

/**
 * @author 惊云
 * @date 2021/12/10 15:14
 */
@Data
public class BinlogPositionEntity {
    /**
     * binlog文件的名字
     */
    private String binlogName;
    /**
     * binlog文件的位置
     */
    private Long position;
    /**
     * binlog的服务id
     */
    private Long serverId;
}
