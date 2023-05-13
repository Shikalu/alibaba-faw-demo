package com.faw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SpringApplication.class}, properties = {"test.prop=value1"}, args = {"--test.prop=value2"})
class SpringTestApplicationTests {
    @Value("${test.prop}")
    private String val;

    @Test
    void test1() {
        System.out.println(val);
        System.out.println();
    }

}
