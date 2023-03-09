package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import com.ebanma.cloud.lambda.BaseCartService;
import com.ebanma.cloud.lambda.CartV2Service;
import com.ebanma.cloud.lambda.Sku;
import com.ebanma.cloud.lambda.SkuCategoryEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Version2Test {

    @Test
    public void filterSkusByCategory() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 查找购物车中图书类商品集合
        List<Sku> result = CartV2Service.filterSkusByCategory(
                cartSkuList, SkuCategoryEnum.BOOKS);

        System.out.println(JSON.toJSONString(
                result, true));
    }
    

}