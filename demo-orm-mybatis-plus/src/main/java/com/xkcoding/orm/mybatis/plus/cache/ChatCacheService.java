package com.xkcoding.orm.mybatis.plus.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ChatCacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void cacheReply(Long userId, String reply) {

        redisTemplate.opsForValue().set(
            "chat:reply:" + userId,
            reply,
            Duration.ofMinutes(10)
        );
    }

    public String getReply(Long userId) {

        return redisTemplate.opsForValue()
            .get("chat:reply:" + userId);
    }
}
