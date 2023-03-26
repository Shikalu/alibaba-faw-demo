package com.faw.dao.impl;

import com.faw.dao.AccountDao;
import com.faw.domain.Account;
import com.faw.util.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class JdbcAccountDaoImpl implements AccountDao {
    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        Connection connection = DruidUtils.getInstance().getConnection();

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
        connection.close();
        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {
        Connection connection = DruidUtils.getInstance().getConnection();
        String sql = "update tb_account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, account.getMoney());
        preparedStatement.setString(2, account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
        return i;
    }
}
