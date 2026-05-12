# Task - Token统计系统

## 本次目标

记录：

```text
prompt_tokens
completion_tokens
total_tokens
```

用于：

- 成本分析
- 用户统计
- 限流
- 后期计费

---

# 当前问题

当前系统：

未记录Token消耗。

---

# 当前相关类

```text
OpenAiClient
AiChatServiceImpl
```

---

# 新增字段

chat_record：

```text
prompt_tokens
completion_tokens
total_tokens
```

---

# 注意事项

1. Token统计统一从AI响应读取
2. 禁止业务层手动计算
3. Token统计必须兼容DeepSeek返回结构

---

# 完成标准

聊天记录支持：

- token统计
- token分析
