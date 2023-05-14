package com.ebanma.cloud.rpc.client;

import com.ebanma.cloud.rpc.config.properties.RpcClientProperties;
import com.ebanma.cloud.rpc.config.properties.RpcProperties;
import com.ebanma.cloud.rpc.utils.zk.ZKServer;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NettyClientGroup implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private ZKServer zkServer;
    /**
     * client属性
     */
    private RpcClientProperties rpcClientProperties;
    /**
     * 全局属性
     */
    private RpcProperties rpcProperties;

    private boolean hasInit = false;

    /**
     * 服务消费者名称
     */
    private String consumerName;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ZKServer getZkServer() {
        return zkServer;
    }

    public void setZkServer(ZKServer zkServer) {
        this.zkServer = zkServer;
    }

    public RpcClientProperties getRpcClientProperties() {
        return rpcClientProperties;
    }

    public void setRpcClientProperties(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }

    public RpcProperties getRpcProperties() {
        return rpcProperties;
    }

    public void setRpcProperties(RpcProperties rpcProperties) {
        this.rpcProperties = rpcProperties;
    }

    public boolean isHasInit() {
        return hasInit;
    }

    public void setHasInit(boolean hasInit) {
        this.hasInit = hasInit;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Map<String, NettyClientBizGroup> getProviders() {
        return providers;
    }

    public void setProviders(Map<String, NettyClientBizGroup> providers) {
        this.providers = providers;
    }

    /**
     * key = 某一类业务的服务提供者名称
     * value = 某一类业务的服务提供者集群
     */
    Map<String, NettyClientBizGroup> providers = new HashMap<>();

    @PostConstruct
    public void postConstruct() throws Exception {
        // 连接NettyServer
        connectProviders();
    }

    /**
     * 更新服务
     */
    public void refreshProviders() throws Exception {
        connectProviders();
    }

    /**
     * 连接NettyServer
     */
    private void connectProviders() throws Exception {

        if (!hasInit) {
            return;
        }

        providers = new HashMap<>();
        String providerPath = rpcProperties.getPath() + rpcProperties.getProviderPath();
        List<String> provders = zkServer.getChild(providerPath);

        for (String provderName : provders) {

            List<String> providerInstance = zkServer.getChild(providerPath + "/" + provderName);

            if (CollectionUtils.isEmpty(providerInstance)) {
                continue;
            }

            NettyClientBizGroup nettyClientBizGroup = new NettyClientBizGroup(provderName, providerInstance);

            providers.put(provderName, nettyClientBizGroup);
        }
    }

    /**
     * 给容器填充属性
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.zkServer = applicationContext.getBean(ZKServer.class);
        this.rpcClientProperties = applicationContext.getBean(RpcClientProperties.class);
        this.rpcProperties = applicationContext.getBean(RpcProperties.class);

        this.consumerName = rpcClientProperties.getConsumerName();

        this.hasInit = true;
    }
}