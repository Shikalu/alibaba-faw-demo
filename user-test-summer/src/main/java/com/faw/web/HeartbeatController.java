package com.faw.web;

import com.faw.service.HeartbeatService;
import com.faw.summer.mvc.annotation.Autowired;
import com.faw.summer.mvc.annotation.Controller;
import com.faw.summer.mvc.annotation.RequestMapping;

/**
 * @author 鹿胜宝
 * @date 2023/03/29
 */
@Controller
@RequestMapping("/test")
public class HeartbeatController {

    @Autowired
    private HeartbeatService heartbeatService;

    @RequestMapping("/heartbeat")
    public String heartbeat(String message) {
        return heartbeatService.getMessage(message);
    }
}
