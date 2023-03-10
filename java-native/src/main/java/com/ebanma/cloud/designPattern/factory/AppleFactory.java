package com.ebanma.cloud.designPattern.factory;

/**
 * @author 鹿胜宝
 * @date 2023/03/02
 */
public class AppleFactory implements FruitFactory {

    @Override
    public Fruit createFruit() {
        return new Apple();
    }
}
