package com.ebanma.cloud.function;

/**
 * @author 鹿胜宝
 * @date 2023/04/08
 */
public class HandlerUser {

    public static BranchHandler isTrueOrFalse(boolean value) {
        return (trueHandler, falseHandler) -> {
            if (value) {
                trueHandler.run();
            } else {
                falseHandler.run();
            }
        };
    }
}
