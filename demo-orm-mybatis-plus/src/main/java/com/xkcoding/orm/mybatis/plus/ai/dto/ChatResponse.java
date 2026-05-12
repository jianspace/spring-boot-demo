package com.xkcoding.orm.mybatis.plus.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI聊天响应
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 包装AI回复内容和Token统计信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    /**
     * AI回复内容
     */
    private String content;

    /**
     * prompt消耗的token
     */
    private Integer promptTokens;

    /**
     * 回复消耗的token
     */
    private Integer completionTokens;

    /**
     * 总token消耗
     */
    private Integer totalTokens;
}
