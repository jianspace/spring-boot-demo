package com.xkcoding.orm.mybatis.plus.ai.prompt.strategy;

import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;

import java.util.List;

/**
 * Prompt策略接口
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 定义应如何根据用户画像、长期记忆、歷史消息构建提示江
 */
public interface PromptStrategy {

    List<ChatMessage> build(
        UserProfile userProfile,
        String memorySummary,
        List<ChatMessage> history,
        String userMessage
    );
}
