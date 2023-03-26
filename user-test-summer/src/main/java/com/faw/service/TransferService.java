package com.faw.service;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public interface TransferService {
    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
