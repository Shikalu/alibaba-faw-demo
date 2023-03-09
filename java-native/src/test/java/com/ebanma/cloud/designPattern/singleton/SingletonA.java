package com.ebanma.cloud.designPattern.singleton;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class SingletonA {
    private static SingletonA instance;

    private SingletonA() {
    }

    public static SingletonA getSingletonA() {
        if (instance == null) {
            instance = new SingletonA();
        }
        return instance;
    }

}
