package com.ebanma.cloud.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class CountDownLatchTest {

    @Test
    public void run() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                Long duration = (long) (Math.random() * 10000);
                try {
                    Thread.sleep(duration);
                    System.out.printf("%d号运动员完成了比赛，耗时%d\n", no, duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            };
            service.submit(runnable);
        }
        System.out.println("等待5个运动员都跑完");
        latch.await();
        System.out.println("所有人都跑完了，比赛结束");
    }

    @Test
    public void run2() throws InterruptedException {
        System.out.println("运动员有5秒的准备时间");
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                System.out.println(no + "号运动员准备完毕，等待裁判员的发令枪");
                try {
                    latch.await();
                    System.out.println(no + "号运动员开始跑步了！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(5000);
        latch.countDown();
        System.out.println("5s时间已过，发令枪响，比赛开始！");
        Thread.sleep(10);
        System.exit(0);
    }
}
