package com.ebanma.cloud.concurrent;

import org.junit.Test;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class MyConditionLinkedQueueTest {
    @Test
    public void p2c() throws InterruptedException {
        MyConditionLinkedQueue queue = new MyConditionLinkedQueue();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int index = 0;
                while (true) {
                    String tmp = Thread.currentThread().getName() + "生产数据" + index;
                    try {
                        queue.put(tmp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(tmp);
                    index++;
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    Object take = null;
                    try {
                        take = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t\t消费数据" + take);
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(1000 * 5);
        System.exit(0);
    }
}
