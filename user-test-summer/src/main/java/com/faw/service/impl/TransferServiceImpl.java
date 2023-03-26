package com.faw.service.impl;

import com.faw.dao.AccountDao;
import com.faw.domain.Account;
import com.faw.service.TransferService;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class TransferServiceImpl implements TransferService {
    private AccountDao accountDao;

    //给反射注入使用
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    //给内省注入使用
    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
//        Connection connection = DruidUtils.getInstance().getConnection();
//        connection.setAutoCommit(false);
//        try {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(from);
//            int i = 1/0;
        accountDao.updateAccountByCardNo(to);
//            connection.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            connection.rollback();
//            throw e;
//        }
    }
}
