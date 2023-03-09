package com.faw.cloud.orm.session;

import com.faw.cloud.orm.model.Configuration;
import com.faw.cloud.orm.model.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装JDBC代码,透传configuration，mappedstatement及参数
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;
}
