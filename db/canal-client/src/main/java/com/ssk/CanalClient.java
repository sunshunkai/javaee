package com.ssk;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author ssk
 * @date 2021/3/2
 *
 *
 * ​
 * Entry=====> RowChange=====> Column
 *
 * ​
 *
 * Entry
 *     Header
 *         logfileName [binlog文件名]
 *         logfileOffset [binlog position]
 *         executeTime [发生的变更]
 *         schemaName
 *         tableName
 *         eventType [insert/update/delete类型]
 *     entryType   [事务头BEGIN/事务尾END/数据ROWDATA]
 *     storeValue  [byte数据,可展开，对应的类型为RowChange]
 *
 *
 * RowChange
 *     isDdl       [是否是ddl变更操作，比如create table/drop table]
 *     sql     [具体的ddl sql]
 *     rowDatas    [具体insert/update/delete的变更数据，可为多条，1个binlog event事件可对应多条变更，比如批处理]
 *         beforeColumns [Column类型的数组]
 *         afterColumns [Column类型的数组]
 *
 *
 * Column
 *     index
 *     sqlType     [jdbc type]
 *     name        [column name]
 *     isKey       [是否为主键]
 *     updated     [是否发生过变更]
 *     isNull      [值是否为null]
 *     value       [具体的内容，注意为文本]
 */
public class CanalClient {
    private static String SERVER_ADDRESS = "127.0.0.1";
    private static Integer PORT = 11111;
    // canal.destinations
    private static String DESTINATION = "example";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    public static void main(String[] args) {
        CanalConnector canalConnector =
                CanalConnectors.newSingleConnector
                        (new InetSocketAddress(SERVER_ADDRESS,PORT),DESTINATION,USERNAME,PASSWORD);
        canalConnector.connect();
        canalConnector.subscribe(".*\\..*");
        canalConnector.rollback();

        for (;;){
            // 获取指定数量的数据，但是不做确认位点信息
            Message message = canalConnector.getWithoutAck(100);
            long msgId = message.getId();
            System.out.println("msgId-->" + msgId);
            if(msgId != -1){
                printEntity(message.getEntries());
                // 确认提交（位点）
                canalConnector.ack(msgId);
                // 处理失败，回滚
//            canalConnector.rollback(msgId);
            }
        }
    }

    private static void printEntity(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if(entry.getEntryType() != CanalEntry.EntryType.ROWDATA){
                continue;
            }

            try {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    switch (rowChange.getEventType()){
                        case INSERT:
                            String tableName = entry.getHeader().getTableName();
                            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                            break;
                        case DELETE:
                            break;
                }



                }
            }catch (Exception e){

            }
        }
    }

}
