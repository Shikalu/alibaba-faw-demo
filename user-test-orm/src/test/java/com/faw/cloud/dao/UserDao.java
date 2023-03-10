package com.faw.cloud.dao;

import com.faw.cloud.domain.entity.UserDO;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public interface UserDao {

    UserDO selectOne(UserDO user) throws DocumentException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    List<UserDO> selectAll() throws DocumentException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;
}
