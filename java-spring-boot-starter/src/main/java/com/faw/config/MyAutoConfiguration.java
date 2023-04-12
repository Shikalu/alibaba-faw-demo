package com.faw.config;

import com.faw.entity.SimpleBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 鹿胜宝
 * @date 2023/04/12
 */
@Configuration
@ConditionalOnBean(ConfigMarker.class)
public class MyAutoConfiguration {
    static {
        System.out.println("配置初始化");
    }

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
