package com.ssk.task;

import org.junit.After;
import org.junit.Assume;
import org.junit.Test;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ssk
 * @date 2021/3/12
 */
public class TaskTest {
    private final StaticApplicationContext context = new StaticApplicationContext();


    @After
    public void closeContextAfterTest() {
        context.close();
    }


    @Test
    public void fixedDelayTask() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(FixedDelayTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedDelayTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedDelayTasks");
        assertEquals(1, fixedDelayTasks.size());
        IntervalTask task = fixedDelayTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedDelay", targetMethod.getName());
        assertEquals(0L, task.getInitialDelay());
        assertEquals(5000L, task.getInterval());
    }

    @Test
    public void fixedRateTask() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(FixedRateTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedRateTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedRateTasks");
        assertEquals(1, fixedRateTasks.size());
        IntervalTask task = fixedRateTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedRate", targetMethod.getName());
        assertEquals(0L, task.getInitialDelay());
        assertEquals(3000L, task.getInterval());
    }

    @Test
    public void fixedRateTaskWithInitialDelay() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(FixedRateWithInitialDelayTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedRateTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedRateTasks");
        assertEquals(1, fixedRateTasks.size());
        IntervalTask task = fixedRateTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedRate", targetMethod.getName());
        assertEquals(1000L, task.getInitialDelay());
        assertEquals(3000L, task.getInterval());
    }

    @Test
    public void severalFixedRatesWithRepeatedScheduledAnnotation() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(SeveralFixedRatesWithRepeatedScheduledAnnotationTestBean.class);
        severalFixedRates(context, processorDefinition, targetDefinition);
    }

    @Test
    public void severalFixedRatesWithSchedulesContainerAnnotation() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(SeveralFixedRatesWithSchedulesContainerAnnotationTestBean.class);
        severalFixedRates(context, processorDefinition, targetDefinition);
    }

    @Test
    public void severalFixedRatesOnBaseClass() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(FixedRatesSubBean.class);
        severalFixedRates(context, processorDefinition, targetDefinition);
    }

    @Test
    public void severalFixedRatesOnDefaultMethod() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(FixedRatesDefaultBean.class);
        severalFixedRates(context, processorDefinition, targetDefinition);
    }

    private void severalFixedRates(StaticApplicationContext context,
                                   BeanDefinition processorDefinition, BeanDefinition targetDefinition) {

        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedRateTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedRateTasks");
        assertEquals(2, fixedRateTasks.size());
        IntervalTask task1 = fixedRateTasks.get(0);
        ScheduledMethodRunnable runnable1 = (ScheduledMethodRunnable) task1.getRunnable();
        Object targetObject = runnable1.getTarget();
        Method targetMethod = runnable1.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedRate", targetMethod.getName());
        assertEquals(0, task1.getInitialDelay());
        assertEquals(4000L, task1.getInterval());
        IntervalTask task2 = fixedRateTasks.get(1);
        ScheduledMethodRunnable runnable2 = (ScheduledMethodRunnable) task2.getRunnable();
        targetObject = runnable2.getTarget();
        targetMethod = runnable2.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedRate", targetMethod.getName());
        assertEquals(2000L, task2.getInitialDelay());
        assertEquals(4000L, task2.getInterval());
    }

    @Test
    public void cronTask() throws InterruptedException {
//        Assume.group(TestGroup.LONG_RUNNING);

        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(CronTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<CronTask> cronTasks = (List<CronTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("cronTasks");
        assertEquals(1, cronTasks.size());
        CronTask task = cronTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("cron", targetMethod.getName());
        assertEquals("*/7 * * * * ?", task.getExpression());
        Thread.sleep(10000);
    }

    @Test
    public void cronTaskWithZone() throws InterruptedException {
//        Assume.group(TestGroup.LONG_RUNNING);

        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(CronWithTimezoneTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<CronTask> cronTasks = (List<CronTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("cronTasks");
        assertEquals(1, cronTasks.size());
        CronTask task = cronTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("cron", targetMethod.getName());
        assertEquals("0 0 0-4,6-23 * * ?", task.getExpression());
        Trigger trigger = task.getTrigger();
        assertNotNull(trigger);
        assertTrue(trigger instanceof CronTrigger);
        CronTrigger cronTrigger = (CronTrigger) trigger;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+10"));
        cal.clear();
        cal.set(2013, 3, 15, 4, 0); // 15-04-2013 4:00 GMT+10
        Date lastScheduledExecutionTime = cal.getTime();
        Date lastActualExecutionTime = cal.getTime();
        cal.add(Calendar.MINUTE, 30); // 4:30
        Date lastCompletionTime = cal.getTime();
        TriggerContext triggerContext = new SimpleTriggerContext(
                lastScheduledExecutionTime, lastActualExecutionTime, lastCompletionTime);
        cal.add(Calendar.MINUTE, 30);
        cal.add(Calendar.HOUR_OF_DAY, 1); // 6:00
        Date nextExecutionTime = cronTrigger.nextExecutionTime(triggerContext);
        assertEquals(cal.getTime(), nextExecutionTime); // assert that 6:00 is next execution time
        Thread.sleep(10000);
    }

//    @Test(expected = BeanCreationException.class)
//    public void cronTaskWithInvalidZone() throws InterruptedException {
//        Assume.group(TestGroup.LONG_RUNNING);
//
//        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
//        BeanDefinition targetDefinition = new RootBeanDefinition(CronWithInvalidTimezoneTestBean.class);
//        context.registerBeanDefinition("postProcessor", processorDefinition);
//        context.registerBeanDefinition("target", targetDefinition);
//        context.refresh();
//        Thread.sleep(10000);
//    }

    @Test(expected = BeanCreationException.class)
    public void cronTaskWithMethodValidation() throws InterruptedException {
        BeanDefinition validationDefinition = new RootBeanDefinition(MethodValidationPostProcessor.class);
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(CronTestBean.class);
        context.registerBeanDefinition("methodValidation", validationDefinition);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();
    }

    @Test
    public void metaAnnotationWithFixedRate() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(MetaAnnotationFixedRateTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedRateTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedRateTasks");
        assertEquals(1, fixedRateTasks.size());
        IntervalTask task = fixedRateTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("checkForUpdates", targetMethod.getName());
        assertEquals(5000L, task.getInterval());
    }

    @Test
    public void metaAnnotationWithCronExpression() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(MetaAnnotationCronTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<CronTask> cronTasks = (List<CronTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("cronTasks");
        assertEquals(1, cronTasks.size());
        CronTask task = cronTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("generateReport", targetMethod.getName());
        assertEquals("0 0 * * * ?", task.getExpression());
    }

    @Test
    public void propertyPlaceholderWithCron() {
        String businessHoursCronExpression = "0 0 9-17 * * MON-FRI";
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition placeholderDefinition = new RootBeanDefinition(PropertyPlaceholderConfigurer.class);
        Properties properties = new Properties();
        properties.setProperty("schedules.businessHours", businessHoursCronExpression);
        placeholderDefinition.getPropertyValues().addPropertyValue("properties", properties);
        BeanDefinition targetDefinition = new RootBeanDefinition(PropertyPlaceholderWithCronTestBean.class);
        context.registerBeanDefinition("placeholder", placeholderDefinition);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<CronTask> cronTasks = (List<CronTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("cronTasks");
        assertEquals(1, cronTasks.size());
        CronTask task = cronTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("x", targetMethod.getName());
        assertEquals(businessHoursCronExpression, task.getExpression());
    }

    @Test
    public void propertyPlaceholderWithFixedDelay() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition placeholderDefinition = new RootBeanDefinition(PropertyPlaceholderConfigurer.class);
        Properties properties = new Properties();
        properties.setProperty("fixedDelay", "5000");
        properties.setProperty("initialDelay", "1000");
        placeholderDefinition.getPropertyValues().addPropertyValue("properties", properties);
        BeanDefinition targetDefinition = new RootBeanDefinition(PropertyPlaceholderWithFixedDelayTestBean.class);
        context.registerBeanDefinition("placeholder", placeholderDefinition);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedDelayTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedDelayTasks");
        assertEquals(1, fixedDelayTasks.size());
        IntervalTask task = fixedDelayTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedDelay", targetMethod.getName());
        assertEquals(1000L, task.getInitialDelay());
        assertEquals(5000L, task.getInterval());
    }

    @Test
    public void propertyPlaceholderWithFixedRate() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition placeholderDefinition = new RootBeanDefinition(PropertyPlaceholderConfigurer.class);
        Properties properties = new Properties();
        properties.setProperty("fixedRate", "3000");
        properties.setProperty("initialDelay", "1000");
        placeholderDefinition.getPropertyValues().addPropertyValue("properties", properties);
        BeanDefinition targetDefinition = new RootBeanDefinition(PropertyPlaceholderWithFixedRateTestBean.class);
        context.registerBeanDefinition("placeholder", placeholderDefinition);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<IntervalTask> fixedRateTasks = (List<IntervalTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("fixedRateTasks");
        assertEquals(1, fixedRateTasks.size());
        IntervalTask task = fixedRateTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("fixedRate", targetMethod.getName());
        assertEquals(1000L, task.getInitialDelay());
        assertEquals(3000L, task.getInterval());
    }

    @Test
    public void propertyPlaceholderForMetaAnnotation() {
        String businessHoursCronExpression = "0 0 9-17 * * MON-FRI";
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition placeholderDefinition = new RootBeanDefinition(PropertyPlaceholderConfigurer.class);
        Properties properties = new Properties();
        properties.setProperty("schedules.businessHours", businessHoursCronExpression);
        placeholderDefinition.getPropertyValues().addPropertyValue("properties", properties);
        BeanDefinition targetDefinition = new RootBeanDefinition(PropertyPlaceholderMetaAnnotationTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("placeholder", placeholderDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();

        Object postProcessor = context.getBean("postProcessor");
        Object target = context.getBean("target");
        ScheduledTaskRegistrar registrar = (ScheduledTaskRegistrar)
                new DirectFieldAccessor(postProcessor).getPropertyValue("registrar");
        @SuppressWarnings("unchecked")
        List<CronTask> cronTasks = (List<CronTask>)
                new DirectFieldAccessor(registrar).getPropertyValue("cronTasks");
        assertEquals(1, cronTasks.size());
        CronTask task = cronTasks.get(0);
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task.getRunnable();
        Object targetObject = runnable.getTarget();
        Method targetMethod = runnable.getMethod();
        assertEquals(target, targetObject);
        assertEquals("y", targetMethod.getName());
        assertEquals(businessHoursCronExpression, task.getExpression());
    }

    @Test(expected = BeanCreationException.class)
    public void emptyAnnotation() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(EmptyAnnotationTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();
    }

    @Test(expected = BeanCreationException.class)
    public void invalidCron() throws Throwable {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(InvalidCronTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();
    }

    @Test(expected = BeanCreationException.class)
    public void nonVoidReturnType() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(NonVoidReturnTypeTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();
    }

    @Test(expected = BeanCreationException.class)
    public void nonEmptyParamList() {
        BeanDefinition processorDefinition = new RootBeanDefinition(ScheduledAnnotationBeanPostProcessor.class);
        BeanDefinition targetDefinition = new RootBeanDefinition(NonEmptyParamListTestBean.class);
        context.registerBeanDefinition("postProcessor", processorDefinition);
        context.registerBeanDefinition("target", targetDefinition);
        context.refresh();
    }


    static class FixedDelayTestBean {

        @Scheduled(fixedDelay=5000)
        public void fixedDelay() {
        }
    }


    static class FixedRateTestBean {

        @Scheduled(fixedRate=3000)
        public void fixedRate() {
        }
    }


    static class FixedRateWithInitialDelayTestBean {

        @Scheduled(fixedRate=3000, initialDelay=1000)
        public void fixedRate() {
        }
    }


    static class SeveralFixedRatesWithSchedulesContainerAnnotationTestBean {

        @Schedules({ @Scheduled(fixedRate = 4000),
                @Scheduled(fixedRate = 4000, initialDelay = 2000) })
        public void fixedRate() {
        }
    }


    static class SeveralFixedRatesWithRepeatedScheduledAnnotationTestBean {

        @Scheduled(fixedRate=4000)
        @Scheduled(fixedRate=4000, initialDelay=2000)
        public void fixedRate() {
        }
    }


    static class FixedRatesBaseBean {

        @Scheduled(fixedRate=4000)
        @Scheduled(fixedRate=4000, initialDelay=2000)
        public void fixedRate() {
        }
    }


    static class FixedRatesSubBean extends FixedRatesBaseBean {
    }


    static interface FixedRatesDefaultMethod {

        @Scheduled(fixedRate=4000)
        @Scheduled(fixedRate=4000, initialDelay=2000)
        default void fixedRate() {
        }
    }


    static class FixedRatesDefaultBean implements FixedRatesDefaultMethod {
    }


    @Validated
    static class CronTestBean {

        @Scheduled(cron="*/7 * * * * ?")
        private void cron() throws IOException {
            throw new IOException("no no no");
        }
    }


    static class CronWithTimezoneTestBean {

        @Scheduled(cron="0 0 0-4,6-23 * * ?", zone = "GMT+10")
        protected void cron() throws IOException {
            throw new IOException("no no no");
        }
    }


    static class CronWithInvalidTimezoneTestBean {

        @Scheduled(cron="0 0 0-4,6-23 * * ?", zone = "FOO")
        public void cron() throws IOException {
            throw new IOException("no no no");
        }
    }


    static class EmptyAnnotationTestBean {

        @Scheduled
        public void invalid() {
        }
    }


    static class InvalidCronTestBean {

        @Scheduled(cron="abc")
        public void invalid() {
        }
    }


    static class NonVoidReturnTypeTestBean {

        @Scheduled(fixedRate=3000)
        public String invalid() {
            return "oops";
        }
    }


    static class NonEmptyParamListTestBean {

        @Scheduled(fixedRate=3000)
        public void invalid(String oops) {
        }
    }


    @Scheduled(fixedRate=5000)
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface EveryFiveSeconds {}


    @Scheduled(cron="0 0 * * * ?")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface Hourly {}


    static class MetaAnnotationFixedRateTestBean {

        @EveryFiveSeconds
        public void checkForUpdates() {
        }
    }


    static class MetaAnnotationCronTestBean {

        @Hourly
        public void generateReport() {
        }
    }


    static class PropertyPlaceholderWithCronTestBean {

        @Scheduled(cron = "${schedules.businessHours}")
        public void x() {
        }
    }


    static class PropertyPlaceholderWithFixedDelayTestBean {

        @Scheduled(fixedDelayString="${fixedDelay}", initialDelayString="${initialDelay}")
        public void fixedDelay() {
        }
    }


    static class PropertyPlaceholderWithFixedRateTestBean {

        @Scheduled(fixedRateString="${fixedRate}", initialDelayString="${initialDelay}")
        public void fixedRate() {
        }
    }


    @Scheduled(cron="${schedules.businessHours}")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private static @interface BusinessHours {
    }


    static class PropertyPlaceholderMetaAnnotationTestBean {

        @BusinessHours
        public void y() {
        }
    }

}
