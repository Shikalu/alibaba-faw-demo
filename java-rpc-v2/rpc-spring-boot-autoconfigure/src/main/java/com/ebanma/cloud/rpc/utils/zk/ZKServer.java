package com.ebanma.cloud.rpc.utils.zk;

import cn.hutool.json.JSONUtil;
import com.ebanma.cloud.rpc.config.properties.RpcProperties;
import com.ebanma.cloud.rpc.model.ProviderBean;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

public class ZKServer implements ApplicationContextAware {

    private RpcProperties rpcProperties;
    private Watcher watcher;

    private String ip;
    private Integer port;
    private String path;
    private String providerPath;
    private String consumerPath;

    ZooKeeper zk;

    public RpcProperties getRpcProperties() {
        return rpcProperties;
    }

    public void setRpcProperties(RpcProperties rpcProperties) {
        this.rpcProperties = rpcProperties;
    }

    public Watcher getWatcher() {
        return watcher;
    }

    public void setWatcher(Watcher watcher) {
        this.watcher = watcher;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProviderPath() {
        return providerPath;
    }

    public void setProviderPath(String providerPath) {
        this.providerPath = providerPath;
    }

    public String getConsumerPath() {
        return consumerPath;
    }

    public void setConsumerPath(String consumerPath) {
        this.consumerPath = consumerPath;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    /**
     * 创建完该对象后创建目录
     */
    @PostConstruct //当依赖注入完成后用于执行初始化的方法，并且只会被执行一次，   Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的初始化方法)
    public void postConstruct() {
        // 初始化
        init();
        // 在zk上创建目录
        createRpcPath();
        // 给某个目录添加监听器
        addWatcher();
    }

    /**
     * 初始化
     */
    private void init() {
        this.ip = rpcProperties.getRegisterAddress();
        this.port = rpcProperties.getServerPort();
        this.path = rpcProperties.getPath();
        this.providerPath = rpcProperties.getProviderPath();
        this.consumerPath = rpcProperties.getConsumerPath();

        String url = ip + ":" + port;

        try {
            zk = new ZooKeeper(url, 500000, watcher);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给某个目录添加监听器
     */
    private void addWatcher() {

        try {
            String listenProviderPath = path + providerPath;
            // 添加自定义监听器
            zk.getChildren(listenProviderPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在zk上创建目录
     */
    private void createRpcPath() {
        try {
            // 创建rpc跟目录
            createPathPermanent(path, "");

            // 创建provider目录
            createPathPermanent(this.path + providerPath, "");

            // 创建consumer目录
            createPathPermanent(this.path + consumerPath, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取子目录节点列表
     *
     * @param path 路径
     * @return {@link List }<{@link String }>
     * @author 鹿胜宝
     */
    public List<String> getChild(String path) throws Exception {
        List<String> children = zk.getChildren(path, true);
        return children;
    }

    /**
     * 向zk注册服务提供者
     *
     * @param providerBean
     */
    public void sendProviderBeanMsg(ProviderBean providerBean) {

        String s = JSONUtil.toJsonStr(providerBean);
    }


    /**
     * 创建永久路径
     *
     * @param path 路径
     * @param data 数据
     * @return {@link String }
     * @author 鹿胜宝
     */
    public String createPathPermanent(String path, String data) throws Exception {
        if (zk.exists(path, true) == null) {
            String mkPath = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Success create path: " + mkPath);
            return mkPath;
        } else {
            return null;
        }
    }

    /**
     * 创建临时路径
     *
     * @param path 路径
     * @param data 数据
     * @return {@link String }
     * @author 鹿胜宝
     */
    public String createPathTemp(String path, String data) throws Exception {
        if (zk.exists(path, true) == null) {
            String mkPath = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("Success create path: " + mkPath);
            return mkPath;
        } else {
            return null;
        }
    }

    //继承了ApplicationContextAware接口，可以对当前bean传入对应的Spring上下文。
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.rpcProperties = applicationContext.getBean(RpcProperties.class);
        this.watcher = applicationContext.getBean(Watcher.class);
    }


    @Override
    public String toString() {
        return "ZKServer{" +
                "rpcProperties=" + rpcProperties +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", providerPath='" + providerPath + '\'' +
                ", consumerPath='" + consumerPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZKServer zkServer = (ZKServer) o;
        return Objects.equals(rpcProperties, zkServer.rpcProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rpcProperties);
    }
}