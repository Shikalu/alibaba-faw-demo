package com.ebanma.cloud.designPattern.singleton;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class SingletonB {
    private static SingletonB instance;

    private SingletonB() {
    }

    public static synchronized SingletonB getInstance() {
        if (instance == null) {
            instance = new SingletonB();
        }
        return instance;
    }
}
