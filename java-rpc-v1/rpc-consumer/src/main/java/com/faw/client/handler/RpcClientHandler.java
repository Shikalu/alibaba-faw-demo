package com.faw.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * 客户端处理类
 * 1.发送消息
 * 2.接收消息
 *
 * @author 鹿胜宝
 * @date 2023/05/06
 */

public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {
    private ChannelHandlerContext context;
    //发送的消息
    private String requestMsg;
    //服务端的消息
    private String responseMsg;

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    /**
     * 通道读取就绪事件
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param msg                     消息
     * @author 鹿胜宝
     */
    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        responseMsg = msg;
        //唤醒等待的线程
        notify();
    }


    /**
     * 通道连接就绪事件
     *
     * @param ctx 通道处理器上下文
     * @author 鹿胜宝
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 发送消息到服务端
     *
     * @return {@link Object }
     * @author 鹿胜宝
     */
    @Override
    public synchronized Object call() throws Exception {
        // 发送消息到服务端
        context.writeAndFlush(requestMsg);
        //线程等待
        wait();
        return responseMsg;
    }
}
