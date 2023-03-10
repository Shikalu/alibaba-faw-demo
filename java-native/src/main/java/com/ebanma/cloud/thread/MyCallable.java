package com.ebanma.cloud.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 多线程方法
 *
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class MyCallable implements Callable<Integer> {
	@Override
    public Integer call() {
        return new Random().nextInt();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> task = new FutureTask<>(myCallable);
        Thread thread = new Thread(task);
        thread.start();
        System.out.println(task.get());

    }

}