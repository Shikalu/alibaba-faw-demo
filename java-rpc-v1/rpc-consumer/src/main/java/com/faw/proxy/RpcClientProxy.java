package com.faw.proxy;

import java.lang.reflect.Proxy;

/**
 * 客户端代理类-创建代理对象
 *
 * @author 鹿胜宝
 * @date 2023/05/06
 */
public class RpcClientProxy {
    public static Object create(Class<?> rpcService) {
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{rpcService},
                new RpcInvocationHandler());
    }
}
