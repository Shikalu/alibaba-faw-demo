package com.ebanma.cloud.designPattern.singleton;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class SingletonD {
    private volatile static SingletonD instance;   //volatile 防止命令重排

    private SingletonD() {
    }

    public static SingletonD getInstance() {
        if (instance == null) {
            synchronized (SingletonD.class) {
                if (instance == null) {            //双重检测锁机制，应对线程A改完的时候，线程B已经完成第一层判断的情况。
                    instance = new SingletonD();
                }
            }
        }
        return instance;
    }
}
