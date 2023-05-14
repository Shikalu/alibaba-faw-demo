package com.ebanma.cloud.rpc.model.constant;

import com.ebanma.cloud.rpc.model.RpcFuture;
import com.ebanma.cloud.rpc.model.RpcResponse;

import java.util.HashMap;

public class FuturePool {

    /**
     * 异步结果暂存
     */
    public static HashMap<String, RpcFuture<RpcResponse>> pool = new HashMap();

    public static void put(String key, RpcFuture future) {
        pool.put(key, future);
    }

    public static RpcFuture<RpcResponse> get(String key) {
        return pool.get(key);
    }

    public static void remove(String key) {
        pool.remove(key);
    }
}