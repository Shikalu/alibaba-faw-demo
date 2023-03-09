package com.ebanma.cloud.designPattern.factory;

/**
 * @author 鹿胜宝
 * @date 2023/03/02
 */
public class FactoryTest {
    public static void main(String[] args) {
        FruitFactory appleFactory = new AppleFactory();
        Fruit fruit = appleFactory.createFruit();
        fruit.produce();

        FruitFactory pineappleFactory = new PineappleFactory();
        Fruit fruit1 = pineappleFactory.createFruit();
        fruit1.produce();
    }


}
