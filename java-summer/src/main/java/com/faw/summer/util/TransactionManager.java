package com.faw.summer.util;

import java.sql.SQLException;

/**
 * 事务管理器
 *
 * @author 鹿胜宝
 * @date 2023/03/29
 */
public class TransactionManager {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils (ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /**
     * 开启手动事务控制
     *
     * @author 鹿胜宝
     */
    public void begin() throws SQLException {
        connectionUtils.getCurrentThreadConnection().setAutoCommit(false);
    }

    /**
     * 提交事务
     *
     * @author 鹿胜宝
     */
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConnection().commit();
    }

    /**
     * 回滚事务
     *
     * @author 鹿胜宝
     */
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConnection().rollback();
    }

    /**
     * 移除threadLocal
     *
     * @author 鹿胜宝
     */
    public void remove() throws SQLException {
        connectionUtils.removeCurrentThreadConnection();
    }
}
