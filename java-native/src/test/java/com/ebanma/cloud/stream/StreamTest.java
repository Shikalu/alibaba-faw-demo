package com.ebanma.cloud.stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebanma.cloud.lambda.BaseCartService;
import com.ebanma.cloud.lambda.Sku;
import com.ebanma.cloud.lambda.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class StreamTest {
    List<Sku> list;

    @Before
    public void init() {
        list = BaseCartService.getCartSkuList();
    }

    @Test
    public void filterTest() {
        list.stream()
                .filter(sku -> SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                .forEach(item -> System.out.println(JSONObject.toJSONString(item, true)));
    }

    @Test
    public void mapTest() {
        list.stream()
                .map(Sku::getSkuName)
                .forEach(System.out::println);
    }

    @Test
    public void flatMapTest() {
        list.stream()
                .flatMap(sku -> Arrays.stream(sku.getSkuName().split("")))
                .forEach(System.out::println);
    }

    @Test
    public void peekTest() {
        list.stream()
                .peek(sku -> System.out.println(JSON.toJSONString(sku.getSkuName(), true)))
                .forEach(sku -> System.out.println(JSON.toJSONString(sku.getSkuName(), true)));
    }

    @Test
    public void sortTest() {
        list.stream()
                .peek(sku -> System.out.println(sku.getSkuName() + sku.getSkuPrice()))
                .sorted(Comparator.comparing(Sku::getSkuPrice))
                .forEach(sku -> System.out.println(sku.getSkuName() + sku.getSkuPrice()));
    }

    @Test
    public void distinctTest() {
        list.stream()
                .map(Sku::getSkuCategory)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void skipTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getSkuPrice))
                .skip(3)
                .forEach(sku -> System.out.println(sku.getSkuName()));
    }

    @Test
    public void limitTest() {
        list.stream()
                .sorted(Comparator.comparing(Sku::getSkuPrice))
                .skip(3)
                .limit(3)
                .forEach(sku -> System.out.println(sku.getSkuName()));
    }

    @Test
    public void allMatchTest() {
        boolean allMatch = list.stream()
                .allMatch(sku -> sku.getSkuPrice() > 10);
        System.out.println(allMatch);
    }

    @Test
    public void anyMatchTest() {
        boolean allMatch = list.stream()
                .anyMatch(sku -> sku.getSkuPrice() > 10000);
        System.out.println(allMatch);
    }

    @Test
    public void noneMatchTest() {
        boolean allMatch = list.stream()
                .noneMatch(sku -> sku.getSkuPrice() > 100);
        System.out.println(allMatch);
    }

    @Test
    public void findFirstTest() {
        Optional<Sku> sku = list.stream()
                .findFirst();
        System.out.println(JSON.toJSONString(sku, true));
    }

    @Test
    public void findAnyTest() {
        Optional<Sku> sku = list.stream()
                .sorted(Comparator.comparing(Sku::getSkuPrice))
                .findAny();
        System.out.println(JSON.toJSONString(sku, true));
    }

    @Test
    public void maxTest() {
        OptionalDouble max = list.stream()
                .mapToDouble(Sku::getSkuPrice)
                .max();
        if (max.isPresent()) {
            System.out.println(max.getAsDouble());
        }
    }

    @Test
    public void minTest() {
        OptionalDouble min = list.stream()
                .mapToDouble(Sku::getSkuPrice)
                .min();
        if (min.isPresent()) {
            System.out.println(min.getAsDouble());
        }
    }

    @Test
    public void countTest() {
        long count = list.stream()
                .map(sku -> JSON.toJSONString(sku, true))
                .peek(System.out::println)
                .count();
        System.out.println(count);
    }

    @Test
    public void numberStreamTest() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
        integerStream.forEach(System.out::println);
    }

    @Test
    public void arrayStreamTest() {
        Integer[] arr = new Integer[]{1, 2, 3};
        Stream<Integer> integerStream = Arrays.stream(arr);
        integerStream.forEach(System.out::println);
    }

    @Test
    public void fileStreamTest() {
        try (Stream<String> stream = Files.lines(Paths.get("D://a.txt"))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void infiniteStreamTest() {
        Stream<Double> stream = Stream.generate(Math::random);
        stream.limit(10)
                .forEach(System.out::println);
    }
}
