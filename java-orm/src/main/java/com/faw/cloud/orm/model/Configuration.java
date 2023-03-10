package com.faw.cloud.orm.model;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration配置文件实体类，其实就是对应config.xml的对象化。
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 映射语句Map。对应xml中的多个mapper
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
