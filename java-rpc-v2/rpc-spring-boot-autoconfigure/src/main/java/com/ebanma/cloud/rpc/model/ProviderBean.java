package com.ebanma.cloud.rpc.model;

public class ProviderBean {
    /**
     * 服务名称
     */
    private String name;
    /**
     * 服务地址，格式：ip:port
     */
    private String address;
    /**
     * 权重，越大优先级越高
     */
    private Integer weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}