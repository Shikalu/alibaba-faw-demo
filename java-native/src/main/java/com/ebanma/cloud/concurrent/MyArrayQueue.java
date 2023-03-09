package com.ebanma.cloud.concurrent;

/**
 * @author 鹿胜宝
 * @date 2023/03/08
 */
public class MyArrayQueue {
    private String[] data = new String[10];

    private int putIndex = 0;
    private int getIndex = 0;
    private int maxSize;

    //多线程抛出异常，可以让线程停下来，优选
    public synchronized void put(String element) throws InterruptedException {
        if (maxSize == data.length) {
            //wait不加同步块，容易死锁，与synchronized配合使用
            wait();
        }
        data[putIndex] = element;
        notifyAll();
        ++maxSize;
        ++putIndex;
        if (putIndex == data.length) {
            putIndex = 0;
        }
    }

    public synchronized String take() throws InterruptedException {
        if (maxSize == 0) {
            wait();
        }
        String result = data[getIndex];
        notifyAll();
        --maxSize;
        ++getIndex;
        if (getIndex == data.length) {
            getIndex = 0;
        }
        return result;
    }
}
