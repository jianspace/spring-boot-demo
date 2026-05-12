# Task - Redis聊天上下文缓存

## 当前情况

当前聊天系统：

- 每次都查询MySQL历史记录
- AI上下文由PromptBuilder拼接
- 已支持多轮对话
- 已支持DeepSeek API

当前问题：

1. MySQL查询频繁
2. 上下文响应慢
3. 后期token会越来越大

---

# 本次目标

实现：

```text
Redis聊天上下文缓存
```

要求：

1. Redis优先读取上下文
2. Redis没有再查MySQL
3. MySQL查询后回填Redis
4. Redis仅保存最近20条消息
5. Redis TTL 30分钟
6. 不改变当前项目整体结构

---

# 当前相关类

```text
ai/prompt/PromptBuilder.java
chat/service/impl/AiChatServiceImpl.java
chat/domain/ChatRecord.java
chat/mapper/ChatRecordMapper.java
```

---

# 新增目录

```text
chat/cache
```

---

# 新增类

```text
ChatContextCache.java
RedisConfig.java
```

---

# Redis Key设计

```text
chat:history:{userId}
```

例如：

```text
chat:history:1
```

---

# Redis Value格式

```json
[
  {
    "role":"user",
    "content":"你好"
  }
]
```

---

# Redis流程

```text
用户请求
    ↓
先查Redis
    ↓
Redis没有：
    查MySQL
    回填Redis
    ↓
拼接messages
    ↓
调用AI
    ↓
用户消息写Redis
AI消息写Redis
    ↓
保存MySQL
```

---

# ChatContextCache接口

```java
List<ChatMessage> getContext(Long userId);

void append(Long userId,
            ChatMessage message);

void clear(Long userId);
```

---

# 注意事项

1. Redis只保存最近20条
2. Redis数据使用JSON序列化
3. 不允许修改数据库表结构
4. 不允许删除当前PromptBuilder
5. 保持当前Controller接口不变

---

# 完成标准

完成后：

用户第一次发送：

```text
我叫简
```

第二次发送：

```text
你记得我叫什么吗
```

AI能正确回答：

```text
你叫简
```

并且：

- Redis存在聊天上下文
- MySQL正常保存聊天记录
- 服务正常启动
