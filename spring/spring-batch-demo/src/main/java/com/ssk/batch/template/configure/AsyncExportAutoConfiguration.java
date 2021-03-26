package com.ssk.batch.template.configure;

import com.ssk.batch.template.annotation.AsyncJob;
import com.ssk.batch.template.job.JobTemplate;
import com.ssk.batch.template.manager.TaskManager;
import com.ssk.batch.template.manager.model.BeanMethod;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ssk
 * @date 2021/3/25
 * 注册任务源
 */
@Slf4j
@ComponentScan("com.ssk.batch.template")
@EnableConfigurationProperties(AsyncExportProperties.class)
@Configuration
public class AsyncExportAutoConfiguration implements ApplicationContextAware {

    private final TaskManager taskManager;

    public AsyncExportAutoConfiguration(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        val beans = applicationContext.getBeansOfType(Object.class).values();
        for (val bean : beans) {
            for (val method : bean.getClass().getMethods()) {
                val annotation = method.getAnnotation(AsyncJob.class);
                if (annotation == null) {
                    continue;
                }
                String jobName;
                if (bean instanceof JobTemplate) {
                    jobName = ((JobTemplate)bean).getJobName();
                } else {
                    jobName = annotation.jobName();
                }
                taskManager.registerTask(jobName, new BeanMethod(bean, method));
            }
        }
    }

}
