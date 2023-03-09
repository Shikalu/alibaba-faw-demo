package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import com.ebanma.cloud.lambda.BaseCartService;
import com.ebanma.cloud.lambda.CartV4Service;
import com.ebanma.cloud.lambda.Sku;
import com.ebanma.cloud.lambda.SkuBooksCategoryPredicate;
import org.junit.Test;

import java.util.List;

public class Version4Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 过滤商品总价大于2000的商品
        List<Sku> result = CartV4Service.filterSkus(
                cartSkuList, new SkuBooksCategoryPredicate());

        System.out.println(JSON.toJSONString(
                result, true));
    }

}