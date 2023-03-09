package com.ebanma.cloud.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class FairAndUnfairDemo {

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue),"Thread" + i);
        }
        for (int i = 0; i < 10; i++) {
            thread[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class Job implements Runnable {
        private PrintQueue printQueue;

        public Job(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            System.out.printf("%s: 去进行打印工作\n", Thread.currentThread().getName());
            printQueue.printJob(new Object());
            System.out.printf("%s: 文档已经打印完\n", Thread.currentThread().getName());
        }
    }

    static class PrintQueue {
        private final Lock queueLock = new ReentrantLock(false);

        public void printJob(Object document) {
            queueLock.lock();
            try {
                Long duration = (long) (Math.random() * 10000);
                System.out.printf("%s: PrintQueue: 打印持续 %d 秒.\n",
                        Thread.currentThread().getName(), (duration / 1000));
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                queueLock.unlock();
            }
            queueLock.lock();
            try {
                Long duration = (long) (Math.random() * 10000);
                System.out.printf("%s: PrintQueue: 打印继续持续 %d 秒.\n",
                        Thread.currentThread().getName(), (duration / 1000));
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                queueLock.unlock();
            }

        }
    }
}
