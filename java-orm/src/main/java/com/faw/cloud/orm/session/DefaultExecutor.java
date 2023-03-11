package com.faw.cloud.orm.session;


import com.faw.cloud.orm.model.BoundSql;
import com.faw.cloud.orm.model.Configuration;
import com.faw.cloud.orm.model.MappedStatement;
import com.faw.cloud.orm.util.GenericTokenParser;
import com.faw.cloud.orm.util.ParameterMapping;
import com.faw.cloud.orm.util.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认执行器
 * 1.获取数据库链接
 * 2.定义sql语句?表示占位符:获取sql语句
 * 3.定义sql语句?表示占位符:转换sql语句
 * 4.获取预处理statement
 * 5.设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值
 * 6.向数据库发出sql执行查询，查询出结果集
 * 7.遍历查询结果集
 * 8.并封装返回对象
 *
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class DefaultExecutor implements Executor {


    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params)
            throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {

        // 1.加载驱动
        // 2.获取数据库连接
        // 在使用getConnection()获取连接的时候就会通过Druid的init()初始化方法的resolveDriver()加载数据库驱动。
        // 参考https://www.cnblogs.com/liconglong/p/16257629.html
        // 参考https://www.cnblogs.com/silenceshining/p/14280140.html?ivk_sa=1024320u
        Connection connection = configuration.getDataSource().getConnection();

        //3.定义sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //4.预处理statement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //5.设置参数，从1开始
        String parameterType = mappedStatement.getParamterType();
        Class<?> parameterTypeClass = parameterType != null ? Class.forName(parameterType) : null;
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            String content = parameterMapping.getContent();
            //返回Field对象
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            //获取实例对象该属性的值
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i + 1, o);
        }
        //6.向数据库发出sql查询
        ResultSet resultSet = preparedStatement.executeQuery();
        //7.遍历结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = resultType != null ? Class.forName(resultType) : null;
        List<Object> resultList = new ArrayList<>();
        while (resultSet.next()) {
            // resultSet.getMetaData()方法用于获取表结构元数据：表名、字段数、字段名
            ResultSetMetaData metaData = resultSet.getMetaData();
            Object instance = resultTypeClass.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //获取字段名
                String columnName = metaData.getColumnName(i);
                //获取字段值
                Object columnValue = resultSet.getObject(columnName);
                // 内省
                // 获取resultTypeClass的某个属性的描述符
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                // 获得用于写入属性值的方法对象
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 写入属性值
                writeMethod.invoke(instance, columnValue);
            }
            resultList.add(instance);
        }
        return (List<E>) resultList;
    }

    @Override
    public int insert(Configuration configuration, MappedStatement mappedStatement, Object... params) {
        return 0;
    }

    private BoundSql getBoundSql(String sql) {
        //1.将#{}用？代替
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);

        //2.解析
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parse, parameterMappings);
    }
}
