package com.ssk.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author ssk
 * @date 2020/12/8
 */
@Component
public class MyDataFlowJob implements DataflowJob<String> {
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("---------获取数据---------");
        return Arrays.asList("1","2","3");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        System.out.println("---------处理数据---------");
        data.forEach(x-> System.out.println("数据处理:"+x));
    }
}
