package com.ssk.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ssk
 * @date 2020/9/11
 */
public class ThreadLocalMain {

    public static void main(String[] args) {

        ThreadLocal<Long> threadLocal = new ThreadLocal(){
            public Long initialValue(){
                return 1L;
            }
        };
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService singleThreadExecutor = Executors.newFixedThreadPool(20);
        for(int i = 0;i<1000;i++){
            singleThreadExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    Long num =  threadLocal.get();
                    System.out.println(Thread.currentThread().getName() + "==========" + num);
                    threadLocal.set(num + 1);
                }
            });
        }
    }


}
