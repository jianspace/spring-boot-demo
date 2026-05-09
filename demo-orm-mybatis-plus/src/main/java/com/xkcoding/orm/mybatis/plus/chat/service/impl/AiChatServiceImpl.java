package com.xkcoding.orm.mybatis.plus.chat.service.impl;

import com.xkcoding.orm.mybatis.plus.ai.AiClient;
import com.xkcoding.orm.mybatis.plus.ai.client.OpenAiClient;
import com.xkcoding.orm.mybatis.plus.ai.prompt.PromptBuilder;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import com.xkcoding.orm.mybatis.plus.mapper.ChatRecordMapper;
import com.xkcoding.orm.mybatis.plus.chat.service.AiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiChatServiceImpl implements AiChatService {

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Autowired
    private PromptBuilder promptBuilder;

    @Autowired
    private AiClient aiClient;
    @Autowired
    private OpenAiClient openAiClient;
    @Override
    public String chat(Long userId, String message) {
        Map<String, Object> columnMap=new HashMap<>();
        columnMap.put("user_id",userId);
        // 查询历史记录
        List<ChatRecord> records = chatRecordMapper.selectByMap(columnMap);
           // chatRecordMapper.selectRecentByUserId(userId);

        // 构建prompt
        String prompt =
            promptBuilder.build(records, message);

        // AI回复
        String reply =
            aiClient.chat(prompt);

        // 保存用户消息
        save(userId, message, "user");

        // 保存AI回复
        save(userId, reply, "assistant");

        return reply;
    }

    @Override
    public String chatGpt(String reqMessage) {
        return openAiClient.chat(reqMessage);
    }

    private void save(Long userId,
                      String msg,
                      String role) {

        ChatRecord record = new ChatRecord();

        record.setUserId(userId);
        record.setMessage(msg);
        record.setRole(role);
        record.setCreateTime(new Date());

        chatRecordMapper.insert(record);
    }
}
