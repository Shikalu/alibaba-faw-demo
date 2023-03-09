package com.faw.cloud.orm.session;

import com.faw.cloud.orm.model.Configuration;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
