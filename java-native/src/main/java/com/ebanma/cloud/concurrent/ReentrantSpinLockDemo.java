package com.ebanma.cloud.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class ReentrantSpinLockDemo {

    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    public void lock() {
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            ++count;
            return;
        }
        //自旋
        while (!owner.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + "自旋了");
        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            if (count > 0) {
                --count;
            } else {
                owner.set(null);
            }
        }
    }
    public static void main(String[] args) {
        ReentrantSpinLockDemo spinLock = new ReentrantSpinLockDemo();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取成功");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放成功");
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
