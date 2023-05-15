package com.faw.client;

import com.faw.client.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * rpc客户端
 * 1.连接Netty服务端
 * 2.提供给调用者主动关闭资源的方法
 * 3.提供消息发送的方法
 *
 * @author 鹿胜宝
 * @date 2023/05/06
 */
public class RpcClient {
    private EventLoopGroup group;
    private Channel channel;
    private String ip;
    private Integer port;
    private RpcClientHandler rpcClientHandler = new RpcClientHandler();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        init();                 //新建对象时初始化
    }

    /**
     * 初始化方法-连接Netty服务端
     *
     * @author 鹿胜宝
     */
    public void init() {
        try {
            //1. 创建线程组
            group = new NioEventLoopGroup();
            //2. 创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            //3. 设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    //String类型编解码器
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    //添加客户端处理类
                                    .addLast(rpcClientHandler);
                        }
                    });
            //4. 连接Netty服务端
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * 提供给调用者主动关闭资源的方法
     *
     * @author 鹿胜宝
     */
    public void close() {
        if (channel != null) {
            channel.close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
    }

    /**
     * 提供消息发送的方法
     *
     * @param msg 消息
     * @return {@link Object }
     * @author 鹿胜宝
     */
    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        return executorService.submit(rpcClientHandler).get();
    }
}
