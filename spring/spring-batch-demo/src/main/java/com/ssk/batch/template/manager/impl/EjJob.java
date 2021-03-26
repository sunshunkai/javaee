package com.ssk.batch.template.manager.impl;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.collect.Lists;

import com.ssk.batch.template.manager.TaskManager;
import com.ssk.batch.template.manager.model.TaskDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author Hugh
 * @date 2018/08/14
 */
@Slf4j
public class EjJob implements DataflowJob<TaskDetail> {

    private TaskManager taskManager = null;

    private TaskManager getTaskManager() {
        if (taskManager == null) {
            taskManager = EjTaskManagerImpl.instance;
            if (taskManager == null) {
                log.info("wait for task manager instance");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                taskManager = getTaskManager();
            }
        }
        return taskManager;
    }

    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List<TaskDetail> fetchData(ShardingContext shardingContext) {
        return Optional.ofNullable(getTaskManager().getTodoTask(shardingContext.getJobName()))
            .map(Lists::newArrayList)
            .orElseGet(Lists::newArrayList);
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data 待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<TaskDetail> data) {
        data.forEach(it -> getTaskManager().executeTask(shardingContext.getJobName(), it));
    }

}
