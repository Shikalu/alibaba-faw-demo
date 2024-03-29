package com.ebanma.cloud.concurrent;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class MyConditionLinkedQueue {
    private LinkedList<Object> data = new LinkedList<>();
    private int maxSize = 10;

    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public void put(Object element) throws InterruptedException {
        lock.lock();
        try {
            while (data.size() == maxSize) {
                notFull.await();
            }
            data.add(element);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (data.size() == 0) {
                notEmpty.await();
            }
            Object remove = data.remove();
            notFull.signalAll();
            return remove;
        } finally {
            lock.unlock();
        }
    }
}
