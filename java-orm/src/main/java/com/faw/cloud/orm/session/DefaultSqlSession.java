package com.faw.cloud.orm.session;

import com.faw.cloud.orm.model.Configuration;
import com.faw.cloud.orm.model.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {

        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        Executor executor = new DefaultExecutor();
        List<Object> resultList = executor.query(configuration, mappedStatement, params);
        return (List<E>) resultList;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        List<Object> list = selectList(statementId, params);
        if (list.size() == 1) {
            return (T) list.get(0);
        }
        throw new SQLException("结果为空或者返回结果过多");
    }

    @Override
    public int insert(String statementId, Object... params) {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        Executor executor = new DefaultExecutor();
        return executor.insert(configuration, mappedStatement, params);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        // ClassLoader loader, 用哪个类加载器去加载代理对象
        // Class<?>[] interfaces, 动态代理类需要实现的接口
        // InvocationHandler h, 动态代理类需要实现的业务逻辑，会调用h里面的invoke方法去执行
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // Object proxy, 当前代理对象的引用
                // Method method, 当前被调用方法的引用
                // Object[] args, 当前被调用方法的参数
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                //拿到方法返回值
                Type genericReturnType = method.getGenericReturnType();
                // ParameterizedType就是参数化类型的意思
                // 声明类型中带有“<>”的都是参数化类型，我们这里特指 selectList 的返回值 List<T>
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId);
                    return objects;
                }
                return selectOne(statementId, args);
            }
        });
        return (T) proxyInstance;
    }

}
