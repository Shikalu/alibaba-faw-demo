package com.ebanma.cloud.function;

/**
 * 分支处理程序
 *
 * @author 鹿胜宝
 * @date 2023/04/08
 */
public interface BranchHandler {
    /**
     * 正确或错误处理程序
     *
     * @param trueHandler  正确处理程序
     * @param falseHandler 错误处理程序
     * @author 鹿胜宝
     */
    void trueOrFalseHandler(Runnable trueHandler, Runnable falseHandler);
}
