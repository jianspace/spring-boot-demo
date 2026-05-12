package com.xkcoding.orm.mybatis.plus.ai.prompt;

import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.ai.prompt.strategy.PromptStrategy;
import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Prompt构建器
 * 版本: 1.1
 * 修改日期: 2026-05-12
 * 修改内容: 重构为策略模式，支持用户画像和长期记忆融合
 */
@Component
public class PromptBuilder {

    @Autowired
    private PromptStrategy promptStrategy;

    public List<ChatMessage> build(
        List<ChatMessage> history,
        String userMessage,
        UserProfile userProfile,
        String memorySummary
    ) {
        return promptStrategy.build(
            userProfile,
            memorySummary,
            history,
            userMessage
        );
    }
}
