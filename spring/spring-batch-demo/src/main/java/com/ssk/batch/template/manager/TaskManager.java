package com.ssk.batch.template.manager;


import com.ssk.batch.template.manager.model.BeanMethod;
import com.ssk.batch.template.manager.model.TaskDetail;

/**
 * 注册任务入口
 */
public interface TaskManager {

    /**
     * 注册任务
     *
     * @param jobName 任务名称
     * @param beanMethod 任务业务逻辑
     */
    void registerTask(String jobName, BeanMethod beanMethod);

    /**
     * 提交任务
     *
     * @param jobName 任务名称
     * @param param 方法参数
     *
     * @return 任务ID
     */
    String submitTask(String jobName, Object... param);

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return 任务详情
     */
    TaskDetail getTaskDetail(String taskId);

    /**
     * 提取一个待进行的任务
     *
     * @param jobName 任务名称
     * @return 任务详情
     */
    TaskDetail getTodoTask(String jobName);

    /**
     * 执行任务
     *
     * @param jobName 任务名称
     * @param task 任务详情
     */
    void executeTask(String jobName, TaskDetail task);

}
