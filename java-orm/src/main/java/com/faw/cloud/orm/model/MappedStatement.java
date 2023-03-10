package com.faw.cloud.orm.model;

/**
 * 映射语句。对应单个的mapper.xml。各个属性是sql语句上的各个属性。
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class MappedStatement {

    /**
     * id
     */
    private String id;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 参数值类型
     */
    private String parameterType;

    /**
     * sql语句
     */
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParamterType() {
        return parameterType;
    }

    public void setParamterType(String paramterType) {
        this.parameterType = paramterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
