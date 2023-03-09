package com.ebanma.cloud.designPattern.builder;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.Test;

/**
 * 产品类
 *
 * @author 鹿胜宝
 * @date 2023/03/02
 */
@Data
public class Product {

    private String background;

    private String icon;

    private String sounds;

    public static class Builder {

        private Product product;

        public Builder() {
            this.product =new Product();
        }

        public Product build() {
            return product;
        }

        public Builder buildBackground(String background) {
            product.setBackground(background);
            return this;
        }

        public Builder buildIcon(String icon) {
            product.setIcon(icon);
            return this;
        }

        public Builder buildSounds(String sounds) {
            product.setSounds(sounds);
            return this;
        }
    }

    @Test
    public void testBuilder() {
        Product.Builder builder = new Builder();
        Product product = builder.buildBackground("my background")
                .buildSounds("my sounds")
                .buildIcon("my icon")
                .build();
        System.out.println(JSON.toJSONString(product,true));
        System.out.println(product);
    }
}
