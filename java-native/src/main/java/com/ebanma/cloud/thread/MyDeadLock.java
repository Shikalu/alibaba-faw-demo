package com.ebanma.cloud.thread;


/**
 * 死锁
 *
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class MyDeadLock {
    Object o1 = new Object();
    Object o2 = new Object();

    public void thread1() throws InterruptedException {
        synchronized (o1) {
            Thread.sleep(1000 * 20);
            synchronized (o2) {
                System.out.println("线程1成功拿到2把锁");
            }
        }
    }

    public void thread2() throws InterruptedException {
        synchronized (o2) {
            Thread.sleep(1000*20);
            synchronized (o1) {
                System.out.println("线程2成功拿到2把锁");
            }
        }
    }

    public static void main(String[] args) {
        MyDeadLock myDeadLock = new MyDeadLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myDeadLock.thread1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myDeadLock.thread2();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
