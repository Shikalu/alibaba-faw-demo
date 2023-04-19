package com.ebanma.cloud.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天室业务处理
 *
 * @author 鹿胜宝
 * @date 2023/04/19
 */
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static List<Channel> channelList = new ArrayList<>();

    /**
     * 上线
     *
     * @param ctx ctx
     * @author 鹿胜宝
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.add(channel);
        System.out.println(channel.remoteAddress().toString().substring(1) + "上线了！");
    }

    /**
     * 下线
     *
     * @param ctx ctx
     * @author 鹿胜宝
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println(channel.remoteAddress().toString().substring(1) + "下线了！");
    }

    /**
     * 读取事件
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param msg                   消息
     * @author 鹿胜宝
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelList.stream()
                .filter(item -> !item.equals(channel))
                .forEach(item -> item.writeAndFlush(channel.remoteAddress().toString().substring(1) + "说：" + msg));
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
        channelList.remove(ctx.channel());
    }

}
