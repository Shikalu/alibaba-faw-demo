package com.ebanma.cloud.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 鹿胜宝
 * @date 2023/03/05
 */
public class ReadWriteLockDemo {
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get readLock!");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release readLock!");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get writeLock!");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " release writeLock!");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(ReadWriteLockDemo::read).start();
        new Thread(ReadWriteLockDemo::read).start();
        new Thread(ReadWriteLockDemo::write).start();
        new Thread(ReadWriteLockDemo::write).start();

    }
}
