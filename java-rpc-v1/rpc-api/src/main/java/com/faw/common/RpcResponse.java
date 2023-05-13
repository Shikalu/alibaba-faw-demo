package com.faw.common;

import lombok.Data;

/**
 * 远程调用响应对象
 *
 * @author 鹿胜宝
 * @date 2023/05/06
 */
@Data
public class RpcResponse {
    /**
     * 响应ID
     */
    private String requestId;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 返回的结果
     */
    private Object result;
}
