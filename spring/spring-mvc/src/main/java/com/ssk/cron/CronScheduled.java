package com.ssk.cron;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CronScheduled {

    String cron() default ""; // 执行时间表达式

    String desc() default  ""; // 任务描述

}
