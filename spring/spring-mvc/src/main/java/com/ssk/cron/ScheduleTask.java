package com.ssk.cron;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author ssk
 * @date 2020/9/10
 */
@Component
public class ScheduleTask implements ApplicationContextAware, BeanFactoryAware, BeanPostProcessor, DisposableBean {

    @Autowired
    private ScheduleJob scheduleJob;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;
    ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(50);
    private boolean init = true;

    public void configureTasks(){
        Class<?> clazz = scheduleJob.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            CronScheduled annoCron = method.getAnnotation(CronScheduled.class);
            if(null == annoCron){
                continue;
            }
            String cron = annoCron.cron();

            if(StringUtils.isEmpty(cron)){
                continue;
            }
            Runnable runnable = new ScheduledMethodRunnable(scheduleJob,method);
            scheduledTaskRegistrar.addTriggerTask(new TriggerTask(runnable, new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    CronTrigger trigger = new CronTrigger(cron);
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                }
            }));

            scheduledTaskRegistrar.setScheduler(scheduler);
            scheduledTaskRegistrar.afterPropertiesSet();


        }

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void destroy() throws Exception {
        scheduledTaskRegistrar.destroy();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(init){
            // 防止多次初始化
            configureTasks();
            init = false;
        }
        return bean;
    }

    /**
     * 提供给外界重新注册定时任务
     */
    public void reReqiester(){
        scheduledTaskRegistrar.destroy();
        scheduledTaskRegistrar.setTriggerTasksList(Lists.newArrayList());
        configureTasks();
    }
}
