package com.ebanma.cloud.threadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class ExecutorsDemo {
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();
    private static final ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(1024),
            namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for (int i = 0; i < 1030; i++) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        pool.shutdown();
    }
}
