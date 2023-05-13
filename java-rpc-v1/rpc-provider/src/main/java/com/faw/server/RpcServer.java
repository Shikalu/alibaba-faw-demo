package com.faw.server;

import com.faw.server.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author 鹿胜宝
 * @date 2023/05/06
 */
@Component
@RequiredArgsConstructor
public class RpcServer implements DisposableBean {
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    private final RpcServerHandler rpcServerHandler;

    public void start(String ip, Integer port) {
        try {
            //1.创建线程组
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup();
            //2.创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //3.设置参数
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    //添加String的编解码器
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    //添加业务处理类
                                    .addLast(rpcServerHandler);
                        }
                    });
            //4.绑定端口
            ChannelFuture sync = serverBootstrap.bind(ip, port).sync();
            System.out.println("自定义RPC框架-服务端启动成功" + ip + ":" + port);
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }

    @Override
    public void destroy() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
