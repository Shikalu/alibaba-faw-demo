package com.faw.cloud.mapper;

import com.faw.cloud.domain.entity.UserDO;
import com.faw.cloud.orm.session.SqlSession;
import com.faw.cloud.orm.session.SqlSessionFactory;
import com.faw.cloud.orm.session.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class UserORMTest {

    @Test
    public void selectOne() throws DocumentException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build("sqlMapConfig.xml");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUsername("username1");
        UserDO user = sqlSession.selectOne("com.faw.cloud.dao.UserDao.selectOne", userDO);
        System.out.println(user);
    }

    @Test
    public void selectList() throws DocumentException, SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build("sqlMapConfig.xml");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<UserDO> userDOList = sqlSession.selectList("com.faw.cloud.dao.UserDao.selectAll");
        System.out.println(userDOList);
    }
}
