package com.ssk.conifg;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.ssk.job.MyDataFlowJob;
import com.ssk.job.MySimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ssk
 * @date 2020/8/21
 */
@Configuration
public class MyJobConfig {

    private final String cron = "0/5 * * * * ?";
    private final int shardingTotalCount = 3;
    private final String shardingItemParameters = "0=A,1=B,2=C";
    private final String jobParameters = "parameter";

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @Bean
    public SimpleJob stockJob() {
        return new MySimpleJob();
    }

    @Bean
    public DataflowJob dataflowJob(){
        return new MyDataFlowJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(),
                cron, shardingTotalCount, shardingItemParameters, jobParameters));
    }


    @Bean(name = "JobScheduler1",initMethod = "init")
    public JobScheduler dataFlowJobScheduler(DataflowJob dataflowJob) {
        return new SpringJobScheduler(dataflowJob, regCenter,
                getDataFlowJobConfiguration(
                        dataflowJob.getClass(),
                        cron,
                        shardingTotalCount,
                        shardingItemParameters,true)
                //,new MyElasticJobListener() 可配置监听器
        );
    }

    /**
     * 创建简单任务详细信息
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @param jobParameters
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters,
                                                         final String jobParameters) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).
                shardingItemParameters(shardingItemParameters).jobParameter(jobParameters).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;

    }

    /**
     *
     * @param jobClass 任务类
     * @param cron
     * @param shardingTotalCount  运行周期配置
     * @param shardingItemParameters  分片个数
     * @param streamingProcess  是否是流式作业
     * @return
     */
    private LiteJobConfiguration getDataFlowJobConfiguration(final Class<? extends DataflowJob> jobClass,
                                                             final String cron,
                                                             final int shardingTotalCount,
                                                             final String shardingItemParameters,
                                                             final Boolean streamingProcess   ){


        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                // true为流式作业,除非fetchData返回数据为null或者size为0,否则会一直执行
                // false 非流式,只会按配置时间执行一次
                , jobClass.getCanonicalName(),streamingProcess)
        ).overwrite(true).build();

    }
}
