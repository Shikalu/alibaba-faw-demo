package com.faw.service.impl;

import com.faw.service.HeartbeatService;
import com.faw.summer.mvc.annotation.Service;

/**
 * @author 鹿胜宝
 * @date 2023/04/01
 */
@Service
public class HeartbeatServiceImpl implements HeartbeatService {

    @Override
    public String getMessage(String message) {
        System.out.println("HeartbeatServiceImpl收到入参：" + message);
        return message;
    }
}
