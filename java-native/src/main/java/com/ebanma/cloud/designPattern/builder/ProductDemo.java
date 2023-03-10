package com.ebanma.cloud.designPattern.builder;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 产品类
 *
 * @author 鹿胜宝
 * @date 2023/03/02
 */
@Data
public class ProductDemo {

    private String background;

    private String icon;

    private String sounds;

    public static class Builder {

        private ProductDemo productDemo;

        public Builder() {
            this.productDemo =new ProductDemo();
        }

        public ProductDemo build() {
            return productDemo;
        }

        public Builder buildBackground(String background) {
            productDemo.setBackground(background);
            return this;
        }

        public Builder buildIcon(String icon) {
            productDemo.setIcon(icon);
            return this;
        }

        public Builder buildSounds(String sounds) {
            productDemo.setSounds(sounds);
            return this;
        }
    }

    public static void main(String[] args) {
        ProductDemo.Builder builder = new Builder();
        ProductDemo productDemo = builder.buildBackground("my background")
                .buildSounds("my sounds")
                .buildIcon("my icon")
                .build();
        System.out.println(JSON.toJSONString(productDemo,true));
        System.out.println(productDemo);
    }
}
