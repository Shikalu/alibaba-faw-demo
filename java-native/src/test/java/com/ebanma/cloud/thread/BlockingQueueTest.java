package com.ebanma.cloud.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author 鹿胜宝
 * @date 2023/03/05
 */
public class BlockingQueueTest {

    @Test
    public void addTest() {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.add(3);
    }

    @Test
    public void removeTest() {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
    }

    @Test
    public void elementTest() {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(2);
        blockingQueue.add(1);
        System.out.println(blockingQueue.element());
    }
}
