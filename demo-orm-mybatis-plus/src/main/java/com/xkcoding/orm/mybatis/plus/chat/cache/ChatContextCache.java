package com.xkcoding.orm.mybatis.plus.chat.cache;

import com.alibaba.fastjson2.JSON;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatContextCache {

    private static final String KEY_PREFIX = "chat:history:";
    private static final int MAX_SIZE = 20;
    private static final Duration TTL = Duration.ofMinutes(30);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public List<ChatMessage> getContext(Long userId) {
        String key = buildKey(userId);
        String value = redisTemplate.opsForValue().get(key);
        if (value == null || value.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<ChatMessage> history = JSON.parseArray(value, ChatMessage.class);
        return history == null ? new ArrayList<>() : new ArrayList<>(history);
    }

    public void append(Long userId, ChatMessage message) {
        List<ChatMessage> history = getContext(userId);
        history.add(0, message);
        while (history.size() > MAX_SIZE) {
            history.remove(history.size() - 1);
        }
        saveContext(userId, history);
    }

    public void clear(Long userId) {
        redisTemplate.delete(buildKey(userId));
    }

    private void saveContext(Long userId, List<ChatMessage> history) {
        String key = buildKey(userId);
        String value = JSON.toJSONString(history);
        redisTemplate.opsForValue().set(key, value, TTL);
    }

    private String buildKey(Long userId) {
        return KEY_PREFIX + userId;
    }
}
