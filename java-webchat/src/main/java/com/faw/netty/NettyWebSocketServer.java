package com.faw.netty;

import com.faw.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Netty服务器
 *
 * @author 鹿胜宝
 * @date 2023/04/19
 */
@RequiredArgsConstructor
@Component
public class NettyWebSocketServer implements Runnable {
    private final NettyConfig nettyConfig;
    private final WebSocketChannelInit webSocketChannelInit;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 资源关闭
     *
     * @author 鹿胜宝
     */
    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void run() {
        try {
            //创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置线程组
            serverBootstrap.group(bossGroup, workerGroup);
            //设置参数
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(webSocketChannelInit);
            //启动
            ChannelFuture channelFuture = serverBootstrap.bind(nettyConfig.getPort()).sync();
            System.out.println("--Netty服务器启动成功--");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
