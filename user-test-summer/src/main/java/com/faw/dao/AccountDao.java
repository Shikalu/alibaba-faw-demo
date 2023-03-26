package com.faw.dao;

import com.faw.domain.Account;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public interface AccountDao {
    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
