package com.ssk.cron;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ssk
 * @date 2020/12/17
 */
public class ScheduledExecutorServiceDemo {

    private static final ScheduledExecutorService EXECUTOR_SERVICE =  Executors.newScheduledThreadPool(1);

    public static void main(String[] args){

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                System.out.println("开始处理钩子...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                System.out.println("钩子处理结束...");
            }
        });

        EXECUTOR_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("定时线程开始执行。。。");
            }
        },10, TimeUnit.SECONDS);
        System.out.println("主程序结束。。。");

    }


}
