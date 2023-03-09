package com.faw.cloud.orm.session;

import com.faw.cloud.orm.config.Resources;
import com.faw.cloud.orm.config.XmlConfigurationBuilder;
import com.faw.cloud.orm.model.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class SqlSessionFactoryBuilder {


    public static SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        //1.解析配置文件
        Configuration configuration = XmlConfigurationBuilder.build(inputStream);
        //2.创建SQL会话对象工厂
        return new DefaultSqlSessionFactory(configuration);
    }

    public static SqlSessionFactory build(String path) throws DocumentException {
        return build(Resources.getResourceAsStream(path));
    }



}
