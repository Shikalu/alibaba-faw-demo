package com.faw.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义处理类
 *
 * @author 鹿胜宝
 * @date 2023/04/19
 */
@Component
@ChannelHandler.Sharable //设置通道共享
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static List<Channel> channelList = new ArrayList<>();

    /**
     * 通道就绪事件-上线
     *
     * @param ctx ctx
     * @author 鹿胜宝
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.add(channel);
        System.out.println("【系统消息】有新的连接");
    }

    /**
     * 通道未就绪事件-下线
     *
     * @param ctx ctx
     * @author 鹿胜宝
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println("【系统消息】" + channel.remoteAddress().toString().substring(1) + "下线了");
    }

    /**
     * 读就绪事件
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param textWebSocketFrame    文本框架网络套接字
     * @author 鹿胜宝
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        System.out.println("【聊天消息】" + msg);
        Channel channel = channelHandlerContext.channel();
        //发送消息排除自身通道
        channelList.stream()
                .filter(item -> !item.equals(channel))
                .forEach(item -> item.writeAndFlush(new TextWebSocketFrame(msg)));
    }

    /**
     * 异常处理
     *
     * @param ctx   ctx
     * @param cause 导致
     * @author 鹿胜宝
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        channelList.remove(channel);
    }
}
