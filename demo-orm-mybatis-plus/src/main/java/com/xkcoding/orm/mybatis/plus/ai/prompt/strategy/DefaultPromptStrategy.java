package com.xkcoding.orm.mybatis.plus.ai.prompt.strategy;

import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 默认Prompt策略
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 将用户画像、长期记忆、聊天历史组织为结构化Prompt
 */
@Component
public class DefaultPromptStrategy implements PromptStrategy {

    @Override
    public List<ChatMessage> build(
        UserProfile userProfile,
        String memorySummary,
        List<ChatMessage> history,
        String userMessage
    ) {
        List<ChatMessage> messages = new ArrayList<>();

        messages.add(buildSystemPrompt(userProfile));

        if (userProfile != null) {
            messages.add(buildUserProfilePrompt(userProfile));
        }

        if (memorySummary != null && !memorySummary.trim().isEmpty()) {
            messages.add(buildMemoryPrompt(memorySummary));
        }

        if (history != null && !history.isEmpty()) {
            Collections.reverse(history);
            messages.addAll(history);
        }

        ChatMessage user = new ChatMessage();
        user.setRole("user");
        user.setContent(userMessage);
        messages.add(user);

        return messages;
    }

    private ChatMessage buildSystemPrompt(UserProfile userProfile) {
        ChatMessage system = new ChatMessage();
        system.setRole("system");

        if (userProfile == null) {
            system.setContent("你是一个温柔、专业的AI助手。回答简洁自然，善于倾听。");
            return system;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("你是一个温柔、专业的AI助手。");

        if (userProfile.getPersonality() != null) {
            builder.append("性格特征：").append(userProfile.getPersonality()).append("。");
        }

        if (userProfile.getStyle() != null) {
            builder.append("回答风格：").append(userProfile.getStyle()).append("。");
        } else {
            builder.append("回答风格：自然亲切、简洁明快。");
        }

        builder.append("请根据用户的兴趣和历史交互给出个性化回答。");

        system.setContent(builder.toString());
        return system;
    }

    private ChatMessage buildUserProfilePrompt(UserProfile userProfile) {
        ChatMessage profile = new ChatMessage();
        profile.setRole("system");

        StringBuilder builder = new StringBuilder();
        builder.append("用户画像：");
        builder.append("姓名：").append(userProfile.getName() == null ? "未知" : userProfile.getName()).append("；");
        builder.append("性格：").append(userProfile.getPersonality() == null ? "友好、礼貌" : userProfile.getPersonality()).append("；");
        builder.append("兴趣：").append(userProfile.getInterests() == null ? "没有特别说明" : userProfile.getInterests()).append("；");
        builder.append("聊天风格：").append(userProfile.getStyle() == null ? "自然、亲切" : userProfile.getStyle()).append("；");
        if (userProfile.getSummary() != null && !userProfile.getSummary().trim().isEmpty()) {
            builder.append("长期记忆摘要：").append(userProfile.getSummary()).append("。");
        }

        profile.setContent(builder.toString());
        return profile;
    }

    private ChatMessage buildMemoryPrompt(String memorySummary) {
        ChatMessage memory = new ChatMessage();
        memory.setRole("system");
        memory.setContent("长期记忆摘要：" + memorySummary);
        return memory;
    }
}
