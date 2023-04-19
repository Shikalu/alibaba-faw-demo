package com.ebanma.cloud.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 鹿胜宝
 * @date 2023/04/19
 */
public class NettyChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 读取事件
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param msg                   消息
     * @author 鹿胜宝
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
    }
}
