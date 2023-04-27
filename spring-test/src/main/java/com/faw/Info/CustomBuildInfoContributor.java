package com.faw.Info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

/**
 * 扩展Info端口
 *
 * @author 鹿胜宝
 * @date 2023/04/27
 */
@Component
public class CustomBuildInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("build", Collections.singletonMap("timestamp", new Date()));
    }
}