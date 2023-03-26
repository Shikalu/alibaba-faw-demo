package com.faw.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class ConnectionUtils {
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    public Connection getCurrentThreadConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            //从连接池中获取链接
            connection = DruidUtils.getInstance().getConnection();
            //将连接绑定到当前线程
            threadLocal.set(connection);
        }
        return connection;
    }

    //remove ThreadLocal 的方法？防止内存泄露？？
}
