package com.xkcoding.orm.mybatis.plus.ai.prompt.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Prompt模板数据结构
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 整理Prompt的各个组成部分（system、profile、memory、context、user）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptTemplate {

    private String system;

    private String profile;

    private String memory;

    private String context;

    private String user;
}
