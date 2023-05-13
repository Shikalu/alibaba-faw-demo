package com.ebanma.cloud.tmpTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

class TestEntity {
    private Double doubleVal;
    private Float floatVal;
    private String name;

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Float getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(Float floatVal) {
        this.floatVal = floatVal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
public class JSONTest {
    public static void main(String[] args) {
        TestEntity testEntity = new TestEntity();
        testEntity.setDoubleVal(123.456001);
        testEntity.setFloatVal(123.45512F);
        testEntity.setName("测试FastJSON");
        int disableDecimalFeature = JSON.DEFAULT_PARSER_FEATURE & ~Feature.UseBigDecimal.getMask();
        String str = JSON.toJSON(testEntity).toString();
//        JSONObject JB = JSON.parseObject(str);
        JSONObject JB = JSON.parseObject(str,JSONObject.class,disableDecimalFeature);
        System.out.println("***** Test Start ******");
        System.out.println("doubleVal = " + JB.get("doubleVal"));
        System.out.println("floatVal = " + JB.get("floatVal"));
        System.out.println("name = " + JB.get("name"));
    }
}
