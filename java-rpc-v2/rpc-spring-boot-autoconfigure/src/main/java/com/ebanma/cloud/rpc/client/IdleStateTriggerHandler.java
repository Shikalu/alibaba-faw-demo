package com.ebanma.cloud.rpc.client;

import cn.hutool.json.JSONUtil;
import com.ebanma.cloud.rpc.model.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleStateTriggerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 通道读事件
     *
     * @param ctx ctx
     * @param s   年代
     * @author 鹿胜宝
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        ctx.channel().writeAndFlush(s);
    }

    // 读写超时并且添加IdleStateHandler时，会触发这个方法
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 获取超时对象，读超时，写超时还是读写超时
        IdleState state = ((IdleStateEvent) evt).state();
        // 如果是读写超时，我这里整的简单了
        if (state.equals(IdleState.ALL_IDLE)) {
            System.out.println("客户端发送心跳");
            // 给服务端发送字符为（HeartBeat-req）的心跳请求
            String s = JSONUtil.toJsonStr(RpcRequest.heartBeatRequest());
            ctx.channel().writeAndFlush(s);
        }
    }
}