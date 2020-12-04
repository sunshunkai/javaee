package com.ssk.cron.executor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ssk
 * @date 2020/12/4
 */
public class ScheduledExecutorTest {

    private static final  ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) {
        for(long i=0;i<10000;i++){
            executorService.schedule(new T(new D(i)),10, TimeUnit.SECONDS);
        }

        executorService.shutdownNow();

    }

    @Data
    public static class T implements Runnable{
        private D i;

        public T(D i){
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"====="+i.getA());
        }
    }

    @Data
    @AllArgsConstructor
    public static class D {
        private Long a;
    }

}
