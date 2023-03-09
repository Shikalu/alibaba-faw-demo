package com.faw.cloud.orm.session;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
