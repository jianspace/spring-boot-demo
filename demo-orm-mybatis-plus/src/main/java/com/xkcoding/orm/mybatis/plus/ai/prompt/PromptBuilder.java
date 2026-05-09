package com.xkcoding.orm.mybatis.plus.ai.prompt;

import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import org.springframework.stereotype.Component;



import java.util.List;

@Component
public class PromptBuilder {

    public String build(List<ChatRecord> records,
                        String message) {

        StringBuilder sb = new StringBuilder();

        sb.append("你是一个智能聊天助手。\n");

        for (ChatRecord r : records) {

            sb.append(r.getRole())
                .append(": ")
                .append(r.getMessage())
                .append("\n");
        }

        sb.append("user: ")
            .append(message);

        return sb.toString();
    }
}
