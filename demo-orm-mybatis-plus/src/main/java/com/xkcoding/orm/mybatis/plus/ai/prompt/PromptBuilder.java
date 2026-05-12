package com.xkcoding.orm.mybatis.plus.ai.prompt;

import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PromptBuilder {

    /**
     * 构建 messages
     */
    public List<ChatMessage> build(
        List<ChatMessage> history,
        String userMessage
    ) {

        List<ChatMessage> messages = new ArrayList<>();

        // system prompt
        ChatMessage system = new ChatMessage();
        system.setRole("system");
        system.setContent("你是一个温柔、专业的AI助手。回答简洁自然。");

        messages.add(system);

        // 历史记录（倒序转正序）
        Collections.reverse(history);

        for (ChatMessage record : history) {
            messages.add(record);
        }

        // 当前用户消息
        ChatMessage user = new ChatMessage();

        user.setRole("user");
        user.setContent(userMessage);

        messages.add(user);

        return messages;
    }
}
