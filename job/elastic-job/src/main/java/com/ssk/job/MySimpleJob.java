package com.ssk.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;


/**
 * @author ssk
 * @date 2020/8/21
 */
public class MySimpleJob implements SimpleJob {


    @Override
    public void execute(ShardingContext shardingContext) {
//        logger.info(String.format("Thread ID: %s, 作业分片总数: %s, " +
//                        "当前分片项: %s.当前参数: %s," +
//                        "作业名称: %s.作业自定义参数: %s"
//                ,
//                Thread.currentThread().getId(),
//                shardingContext.getShardingTotalCount(),
//                shardingContext.getShardingItem(),
//                shardingContext.getShardingParameter(),
//                shardingContext.getJobName(),
//                shardingContext.getJobParameter()
//        ));
        System.out.println( Thread.currentThread().getId() );
        System.out.println( shardingContext.getShardingTotalCount() );
        System.out.println( shardingContext.getShardingItem() );
        System.out.println( shardingContext.getShardingParameter() );
        System.out.println( shardingContext.getJobName() );
        System.out.println( shardingContext.getJobParameter() );
    }

}