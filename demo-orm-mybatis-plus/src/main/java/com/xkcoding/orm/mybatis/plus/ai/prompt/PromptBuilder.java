package com.xkcoding.orm.mybatis.plus.ai.prompt;

import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import org.springframework.stereotype.Component;



import java.util.List;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PromptBuilder {

    /**
     * 构建 messages
     */
    public List<ChatMessage> build(
        List<ChatRecord> history,
        String userMessage
    ) {

        List<ChatMessage> messages =
            new ArrayList<>();

        // system prompt
        ChatMessage system = new ChatMessage();
        system.setRole("system");
        system.setContent("你是一个温柔、专业的AI助手。回答简洁自然。");

        messages.add(system);

        // 历史记录（倒序转正序）
        Collections.reverse(history);

        for (ChatRecord record : history) {

            ChatMessage msg = new ChatMessage();

            msg.setRole(record.getRole());
            msg.setContent(record.getMessage());

            messages.add(msg);
        }

        // 当前用户消息
        ChatMessage user = new ChatMessage();

        user.setRole("user");
        user.setContent(userMessage);

        messages.add(user);

        return messages;
    }
}
