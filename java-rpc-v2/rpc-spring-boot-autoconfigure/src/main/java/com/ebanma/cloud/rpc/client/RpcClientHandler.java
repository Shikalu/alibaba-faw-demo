package com.ebanma.cloud.rpc.client;

import cn.hutool.json.JSONUtil;
import com.ebanma.cloud.rpc.model.RpcFuture;
import com.ebanma.cloud.rpc.model.RpcResponse;
import com.ebanma.cloud.rpc.model.constant.ConstantPool;
import com.ebanma.cloud.rpc.model.constant.FuturePool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 通道读事件
     *
     * @param ctx ctx
     * @param msg 消息
     * @author 鹿胜宝
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("resp:" + msg);

        RpcResponse rpcResponse = JSONUtil.toBean(msg, RpcResponse.class);

        if (ConstantPool.HEART_BEAT.equals(rpcResponse.getRequestId())) {
            System.out.println("客户端接收到心跳");
            return;
        }

        RpcFuture future = FuturePool.get(rpcResponse.getRequestId());

        future.setResponse(rpcResponse);
    }
}