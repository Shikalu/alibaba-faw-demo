package com.faw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对外暴露接口
 *
 * @author 鹿胜宝
 * @date 2023/05/13
 */
@Target(ElementType.TYPE)  //用在接口和类上
@Retention(RetentionPolicy.RUNTIME)  //在运行时可以获取到
public @interface RpcService {
}
