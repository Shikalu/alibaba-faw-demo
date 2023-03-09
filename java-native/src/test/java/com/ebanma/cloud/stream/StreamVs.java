package com.ebanma.cloud.stream;

import com.alibaba.fastjson.JSON;
import com.ebanma.cloud.lambda.BaseCartService;
import com.ebanma.cloud.lambda.Sku;
import com.ebanma.cloud.lambda.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class StreamVs {
    List<Sku> list;
    @Before
    public void init() {
        this.list= BaseCartService.getCartSkuList();
    }
    @Test
    public void oldCartHandler() {
        for (Sku sku : list) {
            System.out.println(JSON.toJSONString(sku,true));
        }
        List<Sku> noteBooks = new ArrayList<>();
        for (Sku sku : list) {
            if(!SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory())){
                noteBooks.add(sku);
            }
        }
        noteBooks.sort(Comparator.comparing(Sku::getTotalPrice).reversed());
        for (Sku noteBook : noteBooks) {
            System.out.println(JSON.toJSONString(noteBook,true));
        }
    }

    @Test
    public void newCartHandle() {
        AtomicInteger money = new AtomicInteger(0);
        List<String> list1 = list.stream()
                .filter(sku -> !SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                .limit(2)
                .peek(sku -> money.set(money.get() + sku.getTotalPrice()))
                .map(sku -> sku.getSkuName())
                .collect(Collectors.toList());
        System.out.println(money);
        System.out.println(list1);
    }
}
