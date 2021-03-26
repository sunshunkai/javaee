package com.ssk.batch.template.manager.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;


import com.ssk.batch.template.configure.AsyncExportProperties;
import com.ssk.batch.template.manager.TaskManager;
import com.ssk.batch.template.manager.model.BeanMethod;
import com.ssk.batch.template.manager.model.TaskDetail;
import com.ssk.batch.template.manager.model.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

/**
 * @author ssk
 * @date 2018/08/14
 */
@Slf4j
abstract class AbstractTaskManagerImpl implements TaskManager {

    final AsyncExportProperties properties;

    @Autowired(required = false)
    private RedisTemplate<String, TaskDetail> redisTemplate;

    private Map<String, Queue<TaskDetail>> queues = Maps.newConcurrentMap();
    private Map<String, BeanMethod> beanMethods = Maps.newConcurrentMap();

    private Map<String, TaskDetail> taskDetails;

    AbstractTaskManagerImpl(AsyncExportProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void postConstruct() {
        if (redisTemplate != null) {
            taskDetails = new DefaultRedisMap<>("task_detail", redisTemplate);
        } else {
            taskDetails = Maps.newConcurrentMap();
        }
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();

    @Override
    public void registerTask(String jobName, BeanMethod beanMethod) {
        registerMethod(jobName, beanMethod);
        registerTaskImpl(jobName, beanMethod);
    }

    /**
     * 任务调度系统注册具体实现
     *
     * @param jobName job名称
     * @param beanMethod 业务逻辑引用
     */
    protected abstract void registerTaskImpl(String jobName, BeanMethod beanMethod);

    private void registerMethod(String jobName, BeanMethod beanMethod) {
        queues.put(jobName, buildQueue(jobName));
        beanMethods.put(jobName, beanMethod);
    }

    private Queue<TaskDetail> buildQueue(String jobName) {
        if (redisTemplate != null) {
            return new DefaultRedisList<>("job_queue:" + jobName, redisTemplate);
        }
        return Queues.newLinkedBlockingQueue();
    }

    @Override
    public String submitTask(String jobName, Object... param) {
        val taskId = UUID.randomUUID().toString();
        val task = new TaskDetail();
        task.setJobName(jobName);
        task.setId(taskId);
        task.setStatus(TaskStatus.SUBMITTED);
        task.setParam(param);
        taskDetails.put(taskId, task);
        queues.computeIfAbsent(jobName, key -> {
            log.warn("no local executor registered for jobName: {}", jobName);
            return buildQueue(jobName);
        }).add(task);
        return taskId;
    }

    @Override
    public TaskDetail getTaskDetail(String taskId) {
        return taskDetails.get(taskId);
    }

    @Override
    public TaskDetail getTodoTask(String jobName) {
        return Optional.ofNullable(queues.get(jobName)).map(Queue::poll).orElse(null);
    }

    @Override
    public void executeTask(String jobName, TaskDetail task) {
        val beanMethod = beanMethods.get(jobName);
        if (beanMethod == null) {
            log.warn("no executor found for jobName: {}", jobName);
            return;
        }

        task.setStatus(TaskStatus.EXECUTING);
        taskDetails.put(task.getId(), task);
        try {
            task.setResult(beanMethod.invoke(Optional.ofNullable(task.getParam()).orElseGet(() -> new Object[1])));
            task.setStatus(TaskStatus.FINISHED);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            task.setStatus(TaskStatus.ERROR);
        }
        taskDetails.put(task.getId(), task);
    }

}
