package com.faw.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 鹿胜宝
 * @date 2023/05/06
 */

@Data
@Builder  //同时使用@Data和@Builder注解时，默认没有构造方法
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String name;
}
