package com.ebanma.cloud.guava;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class OptionalTest {

    @Test
    public void test() throws Throwable{
        Optional.empty();
        Optional.of("books");
        Optional optional = Optional.ofNullable("books");
        optional.isPresent();
        optional.ifPresent(System.out::println);
        optional.orElse("引用缺失");
        optional.orElseGet(()->{return "自定义引用缺失";});
        optional.orElseThrow(()->{throw new RuntimeException("引用缺失异常");});
    }
    public static void stream(List<String> list) {
        Optional.ofNullable(list)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
//        List<String> list = Arrays.asList(new String[]{"1", "2"});
//        stream(list);
        stream(null);
    }
}
