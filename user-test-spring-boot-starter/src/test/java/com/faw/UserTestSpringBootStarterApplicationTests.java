package com.faw;

import com.faw.annotation.EnableRegisterServer;
import com.faw.entity.SimpleBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@EnableRegisterServer
@SpringBootTest
class UserTestSpringBootStarterApplicationTests {

    @Test
    void contextLoads() {
    }

    //测试自定义starter
    @Autowired
    private SimpleBean simpleBean;

    @Test
    public void myStarterTest(){
        System.out.println(simpleBean);
    }

}
