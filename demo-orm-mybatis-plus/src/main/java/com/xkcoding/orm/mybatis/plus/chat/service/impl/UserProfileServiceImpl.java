package com.xkcoding.orm.mybatis.plus.chat.service.impl;

import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;
import com.xkcoding.orm.mybatis.plus.chat.entity.User;
import com.xkcoding.orm.mybatis.plus.chat.service.UserProfileService;
import com.xkcoding.orm.mybatis.plus.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户画像服务实现
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 根据用户信息和聊天历史构建用户画像
 */
@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserService userService;

    @Override
    public UserProfile getProfile(Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return UserProfile.builder()
                .userId(userId)
                .personality("友好、专业")
                .interests("开放式聊天")
                .style("自然、亲切")
                .summary("暂无用户画像，使用默认风格。")
                .build();
        }

        return UserProfile.builder()
            .userId(userId)
            .name(user.getName())
            .personality("友好、乐于助人")
            .interests("技术、生活、兴趣爱好")
            .style("自然、亲切")
            .summary("用户已注册，喜欢深入交流。")
            .build();
    }
}
