package com.ebanma.cloud.rpc.model;

import com.ebanma.cloud.rpc.model.constant.ConstantPool;

import java.io.Serializable;


public class RpcRequest implements Serializable {

    private String requestId;
    /**
     * 请求的服务名
     */
    private String serviceName;
    /**
     * 请求的class类
     */
    private String clazzName;
    /**
     * 请求调用的方法
     */
    private String methodName;

    private Class<?>[] parameterTypes;
    private String[] parameterTypeStrings;

    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String[] getParameterTypeStrings() {
        return parameterTypeStrings;
    }

    public void setParameterTypeStrings(String[] parameterTypeStrings) {
        this.parameterTypeStrings = parameterTypeStrings;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    /**
     * 心跳
     *
     * @return
     */
    public static RpcRequest heartBeatRequest() {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(ConstantPool.HEART_BEAT);
        return rpcRequest;
    }
}