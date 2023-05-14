package com.ebanma.cloud.rpc.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rpc.server")
public class RpcServerProperties {

    /**
     * 服务提供者名称
     */
    private String providerName = "";

    /**
     * 服务提供者端口
     */
    private Integer providerPort = 9527;

    /**
     * 权重，默认为1
     */
    private Integer weight = 1;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Integer getProviderPort() {
        return providerPort;
    }

    public void setProviderPort(Integer providerPort) {
        this.providerPort = providerPort;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}