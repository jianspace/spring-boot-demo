package com.xkcoding.orm.mybatis.plus.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户画像数据模理
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 整合用户的性格、兴趣、聊天风格等维度
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private Long userId;

    private String name;

    private String personality;

    private String interests;

    private String style;

    private String summary;
}
