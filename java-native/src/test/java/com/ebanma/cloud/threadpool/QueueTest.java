package com.ebanma.cloud.threadpool;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 阻塞队列测试
 *
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class QueueTest {

    /**
     * 有界阻塞队列
     *
     * @author 鹿胜宝
     */
    @Test
    public void arrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        for (int i = 0; i < 20; i++) {
            queue.put(1);
            System.out.println("添加" + i);
        }
    }

    /**
     * 无界阻塞队列
     *
     * @author 鹿胜宝
     */
    @Test
    public void linkedBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.put(1);
            System.out.println("添加" + i);
        }
    }

    /**
     * 同步移交阻塞队列
     *
     * @author 鹿胜宝
     */
    @Test
    public void synchronousQueue() throws InterruptedException {
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        //生产者
        new Thread(() -> {
            try {
                synchronousQueue.put(1);
                System.out.println("生产");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        //消费者
        new Thread(() -> {
            try {
                synchronousQueue.take();
                System.out.println("消费");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1000 * 100);
    }
}
