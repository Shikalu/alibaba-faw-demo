package com.ebanma.cloud.concurrent;

import org.junit.Test;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class MyArrayQueueTest {
    @Test
    public void p2c() throws InterruptedException {
        MyArrayQueue queue = new MyArrayQueue();
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
                }
            }).start();

            new Thread(() -> {
                while (true) {
                    String take = null;
                    try {
                        take = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t\t消费数据" + take);
                }
            }).start();
        }
        Thread.sleep(1000 * 5);
        //从内存销毁
        System.exit(10);
    }
}
