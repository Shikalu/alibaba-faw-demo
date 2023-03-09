package com.ebanma.cloud.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class Submit {

    /**
     * submit提交测试
     *
     * @author 鹿胜宝
     */
    @Test
    public void submitTest() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(1000 * 10);
            return 2 * 5;
        });
        Integer integer = future.get();
        System.out.println(integer);
    }

    /**
     * execute提交测试
     *
     * @author 鹿胜宝
     */
    @Test
    public void executeTest() throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(() -> {
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer num = 2 * 5;
            System.out.println(num);
        });
        Thread.sleep(1000 * 20);
    }
}
