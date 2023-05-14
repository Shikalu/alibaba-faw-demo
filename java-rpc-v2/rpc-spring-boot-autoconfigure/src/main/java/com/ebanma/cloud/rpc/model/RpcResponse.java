package com.ebanma.cloud.rpc.model;

import com.ebanma.cloud.rpc.model.constant.ConstantPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class RpcResponse implements Serializable {

    private String requestId;

    private Object returnValue;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 没有服务提供者
     *
     * @return
     */
    public static RpcResponse NO_SERVICE() {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId("404Service");
        rpcResponse.setReturnValue("没有服务提供者");
        return rpcResponse;
    }

    /**
     * 超时TimeOut
     *
     * @return
     */
    public static RpcResponse TIME_OUT(String requestId) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setReturnValue("超时TimeOut");
        return rpcResponse;
    }

    /**
     * 心跳
     *
     * @return
     */
    public static RpcResponse HEART_BEAT() {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(ConstantPool.HEART_BEAT);
        return rpcResponse;
    }


}