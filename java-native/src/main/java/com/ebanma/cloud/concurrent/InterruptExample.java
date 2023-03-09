package com.ebanma.cloud.concurrent;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class InterruptExample {

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            while (!interrupted()) {
                System.out.println(1111);
            }
            System.out.println("Thread end");
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread2 = new MyThread2();
        thread2.start();
        thread2.interrupt();
    }
}
