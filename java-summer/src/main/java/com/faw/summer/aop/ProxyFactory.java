package com.faw.summer.aop;

import com.faw.summer.util.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 鹿胜宝
 * @date 2023/03/29
 */
public class ProxyFactory {
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * jdk代理
     *
     * @param obj obj
     * @return {@link Object }
     * @author 鹿胜宝
     */
    public Object getJdkProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                try {
                    transactionManager.begin();
                    result = method.invoke(obj, args);
                    transactionManager.commit();
                } catch (Exception e) {
                    transactionManager.rollback();
                    throw new Exception(e);
                } finally {
                    transactionManager.close();
                }
                return result;
            }
        });
    }

    /**
     * cglib代理
     *
     * @param obj obj
     * @return {@link Object }
     * @author 鹿胜宝
     */
    public Object getCglibProxy(Object obj) {
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    transactionManager.begin();
                    result = method.invoke(obj, objects);
                    transactionManager.commit();
                } catch (Exception e) {
                    transactionManager.rollback();
                    throw new Exception(e);
                }  finally {
                    transactionManager.close();
                }
                return result;
            }
        });
    }


}
