package com.ssk.batch.template.manager.impl;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import com.ssk.batch.template.configure.AsyncExportProperties;
import com.ssk.batch.template.manager.TaskManager;
import com.ssk.batch.template.manager.model.BeanMethod;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author ssk
 * @date 2021/03/25
 * @desc 任务注册
 */
@Slf4j
@Component
public class EjTaskManagerImpl extends AbstractTaskManagerImpl {

    static TaskManager instance = null;

    private CoordinatorRegistryCenter registryCenter;

    @Autowired(required = false)
    private DataSource dataSource;

    public EjTaskManagerImpl(AsyncExportProperties properties) {
        super(properties);
        registryCenter = registryCenter();
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        instance = this;
    }

    @Override
    protected void registerTaskImpl(String jobName, BeanMethod beanMethod) {
        val coreConfig = JobCoreConfiguration
            .newBuilder(jobName, properties.getCron(), properties.getShardingCount())
            .build();
        val jobConfig = new DataflowJobConfiguration(coreConfig, EjJob.class.getCanonicalName(), true);
        val liteJobConfig = LiteJobConfiguration.newBuilder(jobConfig).overwrite(true).build();

        JobScheduler jobScheduler;
        if (dataSource == null || !properties.getLogToDb()) {
            jobScheduler = new JobScheduler(registryCenter, liteJobConfig);
        } else {
            jobScheduler = new JobScheduler(registryCenter, liteJobConfig, new JobEventRdbConfiguration(dataSource));
        }
        jobScheduler.init();
    }

    private CoordinatorRegistryCenter registryCenter() {
        val config = new ZookeeperConfiguration(properties.getZk().getServerLists(), properties.getZk().getNamespace());
        val registryCenter = new ZookeeperRegistryCenter(config);
        registryCenter.init();
        return registryCenter;
    }

}
