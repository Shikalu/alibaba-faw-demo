package com.faw.common;

import lombok.Data;

/**
 * 远程调用请求对象
 *
 * @author 鹿胜宝
 * @date 2023/05/06
 */
@Data
public class RpcRequest {
    /**
     * 请求对象的ID
     */
    private String requestId;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 入参
     */
    private Object[] parameters;
}
