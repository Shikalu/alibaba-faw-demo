package com.faw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 鹿胜宝
 * @date 2023/04/19
 */
@Controller
public class ChatController {

    @GetMapping("/chat.html")
    public String chat() {
        return "chat";
    }
}
