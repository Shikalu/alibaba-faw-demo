package com.ebanma.cloud.threadpool;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程练习
 *
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class ThreadVs {

    /**
     * 非线程池处理
     *
     * @author 鹿胜宝
     */
    @Test
    public void oldHandle() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "文档处理开始");
                try {
                    Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "文档处理结束");
            }).start();
        }
        Thread.sleep(1000 * 50);
    }

    /**
     * 线程池处理
     *
     * @author 鹿胜宝
     */
    @Test
    public void newHandle() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "文档处理开始");
                try {
                    Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "文档处理结束");
            });
        }
        Thread.sleep(1000 * 50);
    }

}
