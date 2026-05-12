# Task - Token统计系统

## 当前问题

当前系统：

未记录：

```text
prompt_tokens
completion_tokens
total_tokens
```

导致：

- 无法统计AI成本
- 无法分析用户消耗
- 无法做后续限流
- 无法做模型优化

---

# 本次目标

实现：

```text
Token统计系统
```

支持：

1. 记录Token消耗
2. 保存数据库
3. 后续成本分析
4. 后续限流
5. 后续用户计费

---

# 当前相关类

```text
OpenAiClient
AiChatServiceImpl
ChatRecord
ChatRecordMapper
```

---

# 数据库调整

chat_record：

新增字段：

```sql
ALTER TABLE chat_record
ADD COLUMN prompt_tokens INT DEFAULT 0,
ADD COLUMN completion_tokens INT DEFAULT 0,
ADD COLUMN total_tokens INT DEFAULT 0;
```

---

# OpenAI响应结构

需要解析：

```json
usage
```

例如：

```json
"usage": {
  "prompt_tokens": 13,
  "completion_tokens": 305,
  "total_tokens": 318
}
```

---

# OpenAiClient改造

新增：

```text
ChatCompletionResult
```

用于统一返回：

- content
- token统计
- finishReason

---

# 新增DTO

```text
ai/dto/ChatCompletionResult.java
```

---

# ChatCompletionResult字段

```java
String content;

Integer promptTokens;

Integer completionTokens;

Integer totalTokens;

String finishReason;
```

---

# AiChatService改造

聊天完成后：

必须：

保存Token统计。

---

# 注意事项

1. Token统一从AI响应读取
2. 禁止业务层手动估算
3. 必须兼容DeepSeek返回结构
4. SSE流式输出后也必须统计Token

---

# 后续扩展方向

后续支持：

- 用户Token统计
- 每日消耗统计
- 模型成本分析
- 用户额度限制

---

# 完成标准

完成后：

chat_record：

支持：

```text
prompt_tokens
completion_tokens
total_tokens
```

并正确保存。
