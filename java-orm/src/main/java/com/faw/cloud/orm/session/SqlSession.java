package com.faw.cloud.orm.session;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * Sql的会话管理，增删改查
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    int insert(String statementId, Object... params);

    <T> T getMapper(Class<?> mapperClass);
}
