package com.faw.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/26
 */
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象转json
     *
     * @param data 数据
     * @return {@link String }
     * @author 鹿胜宝
     */
    public static String object2Json(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param jsonData json数据
     * @param beanType bean类型
     * @return {@link T }
     * @author 鹿胜宝
     */
    public static <T> T json2Object(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转pojo列表
     *
     * @param jsonData json数据
     * @param beanType bean类型
     * @return {@link List }<{@link T }>
     * @author 鹿胜宝
     */
    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
