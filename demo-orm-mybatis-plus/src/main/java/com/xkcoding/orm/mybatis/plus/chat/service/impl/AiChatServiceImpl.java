package com.xkcoding.orm.mybatis.plus.chat.service.impl;





import com.xkcoding.orm.mybatis.plus.ai.client.OpenAiClient;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.ai.prompt.PromptBuilder;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import com.xkcoding.orm.mybatis.plus.chat.mapper.ChatRecordMapper;
import com.xkcoding.orm.mybatis.plus.chat.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatServiceImpl
    implements AiChatService {

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Autowired
    private PromptBuilder promptBuilder;

    @Autowired
    private OpenAiClient openAiClient;

    @Override
    public String chat(Long userId,
                       String message) {
        Map<String, Object> columnMap=new HashMap<>();
        columnMap.put("user_id",userId);
        // 查询最近聊天记录
        List<ChatRecord> history =chatRecordMapper.selectByMap(columnMap);

        // 构建messages
        List<ChatMessage> messages =
            promptBuilder.build(
                history,
                message
            );

        // AI回复
        String reply =
            openAiClient.chat(messages);

        // 保存用户消息
        save(userId,
            "user",
            message);

        // 保存AI消息
        save(userId,
            "assistant",
            reply);

        return reply;
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
