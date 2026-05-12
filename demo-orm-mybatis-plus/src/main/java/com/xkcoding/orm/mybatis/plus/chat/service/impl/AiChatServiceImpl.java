package com.xkcoding.orm.mybatis.plus.chat.service.impl;





import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkcoding.orm.mybatis.plus.ai.client.OpenAiClient;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatResponse;
import com.xkcoding.orm.mybatis.plus.ai.prompt.PromptBuilder;
import com.xkcoding.orm.mybatis.plus.chat.cache.ChatContextCache;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import com.xkcoding.orm.mybatis.plus.chat.memory.LongTermMemoryService;
import com.xkcoding.orm.mybatis.plus.chat.mapper.ChatRecordMapper;
import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;
import com.xkcoding.orm.mybatis.plus.chat.service.UserProfileService;
import com.xkcoding.orm.mybatis.plus.chat.service.AiChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AI聊天服务实现
 * 版本: 1.2
 * 修改日期: 2026-05-12
 * 修改内容: 集成用户画像、长期记忆、Prompt策略；添加日志规范
 */
@Slf4j
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

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private LongTermMemoryService longTermMemoryService;

    @Override
    public String chat(Long userId,
                       String message) {

        log.debug("用户 {} 发起聊天: {}", userId, message);

        UserProfile userProfile = userProfileService.getProfile(userId);
        String memorySummary = longTermMemoryService.summarize(userId);

        List<ChatMessage> history = chatContextCache.getContext(userId);

        if (history.isEmpty()) {
            history = loadFromDb(userId);
            if (!history.isEmpty()) {
                cacheContext(userId, history);
                log.debug("从MySQL加载并缓存了 {} 条历史记录", history.size());
            }
        }

        // 构建messages
        List<ChatMessage> messages = promptBuilder.build(
            history,
            message,
            userProfile,
            memorySummary
        );

        log.debug("构建的Messages数量: {}", messages.size());

        // AI回复并统计Token
        ChatResponse response = openAiClient.chat(messages);
        String reply = response.getContent();

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
        save(userId, "user", message, 0, 0, 0);
        save(userId, "assistant", reply,
            response.getPromptTokens(),
            response.getCompletionTokens(),
            response.getTotalTokens());

        log.debug("聊天记录已保存");

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
                      String message,
                      Integer promptTokens,
                      Integer completionTokens,
                      Integer totalTokens) {

        ChatRecord record =
            new ChatRecord();

        record.setUserId(userId);
        record.setRole(role);
        record.setMessage(message);
        record.setPromptTokens(promptTokens);
        record.setCompletionTokens(completionTokens);
        record.setTotalTokens(totalTokens);
        record.setCreateTime(new Date());

        chatRecordMapper.insert(record);
    }
}
