package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import com.ebanma.cloud.lambda.BaseCartService;
import com.ebanma.cloud.lambda.Sku;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class Version5Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 过滤商品总价大于2000的商品
        List<Sku> collect = cartSkuList.stream()
                .filter((Sku sku) -> sku.getTotalPrice() > 3000)
                .collect(Collectors.toList());

        System.out.println(JSON.toJSONString(
                collect, true));

    }

}