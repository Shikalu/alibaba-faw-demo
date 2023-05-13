package com.faw.proxy;

import com.alibaba.fastjson.JSON;
import com.faw.client.RpcClient;
import com.faw.common.RpcRequest;
import com.faw.common.RpcResponse;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * rpc调用处理程序
 * 1.封装request请求对象
 * 2.创建RpcClient对象
 * 3.发送消息
 * 4.返回结果
 *
 * @author 鹿胜宝
 * @date 2023/05/07
 */
public class RpcInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        //1.封装request请求对象
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        //2.创建RpcClient对象
        RpcClient rpcClient = new RpcClient("127.0.0.1", 8888);
        try {
            //3.发送消息
            Object response = rpcClient.send(JSON.toJSONString(rpcRequest));
            RpcResponse rpcResponse = JSON.parseObject(response.toString(), RpcResponse.class);
            if (rpcResponse.getError() != null) {
                throw new RuntimeException(rpcResponse.getError());
            }
            //4.返回结果
            Object result = rpcResponse.getResult();
            return JSON.parseObject(result.toString(), method.getReturnType());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            rpcClient.close();
        }
    }
}
