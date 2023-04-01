package com.faw.summer.util;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class DruidUtils {

    public DruidUtils() {
    }

    private static DruidDataSource druidDataSource = new DruidDataSource();

    static {
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/db_test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }
}
