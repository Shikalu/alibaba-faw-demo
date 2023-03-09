package com.ebanma.cloud.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 鹿胜宝
 * @date 2023/03/05
 */
public class ConditionDemo {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    void method1() throws InterruptedException {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "：条件不满足，开始等待");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "：条件满足了，开始执行");
        } finally {
            reentrantLock.unlock();
        }
    }

    void method2() throws InterruptedException {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "：需要5秒钟的准备时间");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "：准备工作完成，唤醒其它线程");
            condition.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo conditionDemo = new ConditionDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conditionDemo.method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        conditionDemo.method1();
    }
}
