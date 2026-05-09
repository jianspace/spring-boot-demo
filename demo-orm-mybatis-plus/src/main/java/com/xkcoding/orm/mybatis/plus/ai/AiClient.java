package com.xkcoding.orm.mybatis.plus.ai;

import org.springframework.stereotype.Component;


@Component
public class AiClient {

    public String chat(String prompt) {

        System.out.println("AI Prompt:");
        System.out.println(prompt);

        // mock
        return "这是AI回复";
    }
}
