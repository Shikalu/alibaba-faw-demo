package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import com.ebanma.cloud.lambda.CartV1Service;
import com.ebanma.cloud.lambda.Sku;
import org.junit.Test;

import java.util.List;

public class VersionTest {
    @Test
    public void filterElectronicsSkus(){
        List<Sku> cartSkuList = CartV1Service.getCartSkuList();
        List<Sku> skus = CartV1Service.filterElectronicsSkus(cartSkuList);
        System.out.println(JSON.toJSONString(skus,true));
    }
}
