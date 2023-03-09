package com.faw.cloud.orm.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.faw.cloud.orm.model.Configuration;
import com.faw.cloud.orm.model.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class XmlConfigurationBuilder {
    private static Configuration configuration = new Configuration();

    public static Configuration build(InputStream inputStream) throws DocumentException {
        //使用dom4j对xml解析
        Document document = new SAXReader().read(inputStream);
        //configuration
        Element rootElement = document.getRootElement();
        List<Element> propertyList = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        propertyList.forEach((Element element) -> {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        });

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClass"));
        dataSource.setUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(dataSource);

        List<Element> mapperList = rootElement.selectNodes("//mapper");
        parseMapperXML(mapperList);
        return configuration;
    }

    private static void parseMapperXML(List<Element> mapperList) {
        mapperList.forEach((Element mapper) -> {
            String path = mapper.attributeValue("resource");
            InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
            try {
                Document document = new SAXReader().read(resourceAsStream);
                Element rootElement = document.getRootElement();
                String namespace = rootElement.attributeValue("namespace");
                List<Element> selectList = rootElement.selectNodes("//select");

                selectList.forEach((Element select) -> {
                    String id = select.attributeValue("id");
                    String resultType = select.attributeValue("resultType");
                    String parameterType = select.attributeValue("parameterType");
                    String sqlText = select.getTextTrim();

                    MappedStatement mappedStatement = new MappedStatement();
                    mappedStatement.setId(id);
                    mappedStatement.setResultType(resultType);
                    mappedStatement.setParamterType(parameterType);
                    mappedStatement.setSql(sqlText);

                    configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
                });
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
