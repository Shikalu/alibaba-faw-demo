package com.faw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

/**
 * @author 鹿胜宝
 * @date 2023/04/25
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//开启虚拟MVC调用
@AutoConfigureMockMvc
public class WebTest {

    /**
     * 测试请求状态
     *
     * @param mvc mvc
     * @author 鹿胜宝
     */
    @Test
    void testStatus(@Autowired MockMvc mvc) throws Exception {
        //创建虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        //执行虚拟请求
        ResultActions perform = mvc.perform(builder);
        //设定预期请求结果状态
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();
        //执行结果与预期结果对比
        perform.andExpect(ok);
    }

    /**
     * 测试请求体
     *
     * @param mvc mvc
     * @author 鹿胜宝
     */
    @Test
    void testBody(@Autowired MockMvc mvc) throws Exception {
        //创建虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        //执行虚拟请求
        ResultActions perform = mvc.perform(builder);
        //设定预期请求结果
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher body = content.string("books");
        //执行结果与预期结果对比
        perform.andExpect(body);
    }

    /**
     * 测试请求体（json）
     *
     * @param mvc mvc
     * @author 鹿胜宝
     */
    @Test
    void testJson(@Autowired MockMvc mvc) throws Exception {
        //创建虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        //执行虚拟请求
        ResultActions perform = mvc.perform(builder);
        //设定预期请求结果
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher body = content.json("{\"id\":\"1\",\"name\":\"Spring\"}");
        //执行结果与预期结果对比
        perform.andExpect(body);
    }

    /**
     * 测试ContentType
     *
     * @param mvc mvc
     * @author 鹿胜宝
     */
    @Test
    void testContentType(@Autowired MockMvc mvc) throws Exception {
        //创建虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        //执行虚拟请求
        ResultActions perform = mvc.perform(builder);
        //设定预期请求结果
        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher string = header.string("Content-Type", "application/json");
        //执行结果与预期结果对比
        perform.andExpect(string);
    }

    /**
     * 测试getList
     *
     * @param mvc mvc
     * @author 鹿胜宝
     */
    @Test
    void testGetList(@Autowired MockMvc mvc) throws Exception {
        //创建虚拟请求
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");
        //执行虚拟请求
        ResultActions perform = mvc.perform(builder);

        //测试状态
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk();
        perform.andExpect(ok);

        //测试ContentType
        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher string = header.string("Content-Type", "application/json");
        perform.andExpect(string);

        //测试请求体内容
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher body = content.json("{\"id\":\"1\",\"name\":\"Spring\"}");
        perform.andExpect(body);
    }
}
