package com.faw.cloud.orm.config;

import java.io.InputStream;

/**
 * @author 鹿胜宝
 * @date 2023/03/09
 */
public class Resources {
    /**
     * 将配置文件加载为字节流，存储在内存中
     *
     * @param path 路径
     * @return {@link InputStream }
     * @author 鹿胜宝
     */
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
