package com.xkcoding.orm.mybatis.plus.chat.controller;


import com.xkcoding.orm.mybatis.plus.chat.dto.ChatRequest;
import com.xkcoding.orm.mybatis.plus.chat.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {

        return aiChatService.chat(
            request.getUserId(),
            request.getMessage()
        );
    }

    @PostMapping("/chatgpt")
    public String ai(@RequestBody ChatRequest request) {

      //  return aiChatService.chatGpt( request.getMessage());
        return "";
    }
}
