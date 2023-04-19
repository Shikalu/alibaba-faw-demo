package com.faw.netty;

import com.faw.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 通道初始化对象
 *
 * @author 鹿胜宝
 * @date 2023/04/19
 */
@Component
@RequiredArgsConstructor
public class WebSocketChannelInit extends ChannelInitializer<Channel> {
    private final NettyConfig nettyConfig;
    private final WebSocketHandler webSocketHandler;

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                //对http协议的支持
                .addLast(new HttpServerCodec())
                //对大数据流的支持
                .addLast(new ChunkedWriteHandler())
                //post请求三部分，request line/request header/ message body
                //HttpObjectAggregator将多个信息转化成单一的request或者response对象
                .addLast(new HttpObjectAggregator(8000))
                //将http协议升级为ws协议. websocket的支持
                .addLast(new WebSocketServerProtocolHandler(nettyConfig.getPath()))
                //自定义处理handler
                .addLast(webSocketHandler);

    }
}
