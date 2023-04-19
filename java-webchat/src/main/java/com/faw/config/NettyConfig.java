package com.faw.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Netty配置类
 *
 * @author 鹿胜宝
 * @date 2023/04/19
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    //netty监听的端口
    private Integer port;

    //websocket访问路径
    private String path;
}
