package com.faw.cloud.orm.model;

import com.faw.cloud.orm.util.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql语句封装
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class BoundSql {

    /**
     * 解析过后的sql
     */
    private String sqlText;

    /**
     * 参数映射列表
     */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
