package com.ebanma.cloud.designPattern.singleton;

/**
 * @author 鹿胜宝
 * @date 2023/03/04
 */
public class SingletonC {
    private static SingletonC instance = new SingletonC();

    private SingletonC() {
    }

    public static SingletonC getInstance() {
        return instance;
    }
}
