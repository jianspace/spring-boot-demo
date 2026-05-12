package com.xkcoding.orm.mybatis.plus.chat.service.impl;





import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkcoding.orm.mybatis.plus.ai.client.OpenAiClient;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.ai.prompt.PromptBuilder;
import com.xkcoding.orm.mybatis.plus.chat.cache.ChatContextCache;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import com.xkcoding.orm.mybatis.plus.chat.mapper.ChatRecordMapper;
import com.xkcoding.orm.mybatis.plus.chat.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AiChatServiceImpl
    implements AiChatService {

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Autowired
    private PromptBuilder promptBuilder;

    @Autowired
    private OpenAiClient openAiClient;

    @Autowired
    private ChatContextCache chatContextCache;

    @Override
    public String chat(Long userId,
                       String message) {

        List<ChatMessage> history = chatContextCache.getContext(userId);

        if (history.isEmpty()) {
            history = loadFromDb(userId);
            if (!history.isEmpty()) {
                cacheContext(userId, history);
            }
        }

        // 构建messages
        List<ChatMessage> messages =
            promptBuilder.build(
                history,
                message
            );

        // AI回复
        String reply = openAiClient.chat(messages);

        // 写Redis历史上下文
        ChatMessage userMessage = new ChatMessage();
        userMessage.setRole("user");
        userMessage.setContent(message);
        chatContextCache.append(userId, userMessage);

        ChatMessage assistantMessage = new ChatMessage();
        assistantMessage.setRole("assistant");
        assistantMessage.setContent(reply);
        chatContextCache.append(userId, assistantMessage);

        // 保存数据库
        save(userId, "user", message);
        save(userId, "assistant", reply);

        return reply;
    }

    private List<ChatMessage> loadFromDb(Long userId) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
            .orderByDesc("create_time");

        List<ChatRecord> records = chatRecordMapper.selectList(queryWrapper);
        List<ChatMessage> history = new ArrayList<>();

        for (ChatRecord record : records) {
            ChatMessage message = new ChatMessage();
            message.setRole(record.getRole());
            message.setContent(record.getMessage());
            history.add(message);
        }

        return history;
    }

    private void cacheContext(Long userId,
                              List<ChatMessage> history) {
        for (int i = history.size() - 1; i >= 0; i--) {
            chatContextCache.append(userId, history.get(i));
        }
    }

    /**
     * 保存聊天记录
     */
    private void save(Long userId,
                      String role,
                      String message) {

        ChatRecord record =
            new ChatRecord();

        record.setUserId(userId);
        record.setRole(role);
        record.setMessage(message);
        record.setCreateTime(new Date());

        chatRecordMapper.insert(record);
    }
}
