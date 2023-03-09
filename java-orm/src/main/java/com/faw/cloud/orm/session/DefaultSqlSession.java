package com.faw.cloud.orm.session;

import com.faw.cloud.orm.model.Configuration;
import com.faw.cloud.orm.model.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {

        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        Executor executor = new DefaultExecutor();
        List<Object> resultList = executor.query(configuration, mappedStatement, params);
        return (List<E>) resultList;
    }

    @Override
    public <T> T selectOne(String statementId, Object... objects) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Object> list = selectList(statementId, objects);
        if(list.size() == 1) {
            return (T) list.get(0);
        }
        throw  new SQLException("结果为空或者返回结果过多");
    }

}
