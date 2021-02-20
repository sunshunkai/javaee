package com.ssk.concurrent;

import lombok.SneakyThrows;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ssk
 * @date 2020/12/23
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Sync sync = new Sync();
        CompletableFuture<Integer> future = new CompletableFuture<>();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("线程启动");
                Thread.sleep(3000);
                future.complete(11);
//                throw new RuntimeException();
            }
        }).start();

        future.whenComplete((obj, t) -> {
            if (t != null) {
                future.completeExceptionally(t);
            } else {
                System.out.println("-----"+obj);
                future.complete(obj);
            }
        });

        System.out.println("+++"+future.get());
    }

    public static class Sync implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(10000);
            return 1;
        }
    }
}
