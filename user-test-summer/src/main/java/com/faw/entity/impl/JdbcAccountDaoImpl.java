package com.faw.entity.impl;

import com.faw.entity.AccountDao;
import com.faw.domain.Account;
import com.faw.summer.util.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class JdbcAccountDaoImpl implements AccountDao {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        //实现事务回滚，需要用同一个线程的连接，这里借助ThreadLocal，隔离并存存取自己的线程。
        Connection connection = connectionUtils.getCurrentThreadConnection();

        String sql = "select * from tb_account where cardNo = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cardNo);
        ResultSet resultSet = ps.executeQuery();

        Account account = new Account();

        while (resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }
        resultSet.close();
        ps.close();
        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {
        //实现事务回滚，需要用同一个线程的连接，这里借助ThreadLocal，隔离并存存取自己的线程。
        Connection connection = connectionUtils.getCurrentThreadConnection();
        String sql = "update tb_account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getMoney());
        preparedStatement.setString(2, account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        return i;
    }
}
