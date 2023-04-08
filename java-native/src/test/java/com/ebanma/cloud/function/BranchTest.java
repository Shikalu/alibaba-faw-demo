package com.ebanma.cloud.function;

import org.junit.Test;

/**
 * @author 鹿胜宝
 * @date 2023/04/08
 */
public class BranchTest {

    @Test
    public void test() {
        HandlerUser.isTrueOrFalse(true)
                .trueOrFalseHandler(
                        () -> {
                            System.out.println("true!");
                        },
                        () -> {
                            System.out.println("false!");
                        }
                );
    }
}
