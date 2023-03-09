package com.ebanma.cloud.threadpool;


import org.junit.After;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 饱和策略
 *
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class PolicyTest {
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 3,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(5));

    class Task implements Runnable {
        private String taskName;

        public Task(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println("线程【" + Thread.currentThread().getName() + "】正在执行" + taskName + "任务……");
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程【" + Thread.currentThread().getName() + "】完成" + taskName + "任务！！！");
        }
    }

    /**
     * 通用后处理方法
     *
     * @author 鹿胜宝
     */
    @After
    public void after() throws InterruptedException {
        Thread.sleep(1000 * 20);
    }


    /**
     * 终止策略，抛出异常
     *
     * @author 鹿胜宝
     */
    @Test
    public void abortPolicyTest() {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Task("任务" + i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    /**
     * 丢弃策略
     *
     * @author 鹿胜宝
     */
    @Test
    public void discardPolicyTest() {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Task("任务" + i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    /**
     * 丢弃最久任务的策略
     *
     * @author 鹿胜宝
     */
    @Test
    public void discardOldestPolicyTest() {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Task("任务" + i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    /**
     * 调用者运行策略，饱和后将任务返回给main
     *
     * @author 鹿胜宝
     */
    @Test
    public void callerRunsPolicyTest() {
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            try {
                executor.execute(new Task("任务" + i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
