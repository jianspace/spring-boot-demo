# Task - Session会话系统

## 当前问题

当前系统：

```text
一个userId = 一个聊天
```

无法支持：

- 多聊天
- 聊天切换
- 聊天历史
- 会话隔离

---

# 本次目标

实现：

```text
Session会话系统
```

支持：

1. 一个用户多个聊天
2. 每个聊天独立上下文
3. 每个聊天独立Redis缓存
4. 会话历史管理

---

# 数据表设计

新增：

```text
chat_session
```

字段：

```text
id
user_id
title
summary
create_time
update_time
```

---

# Redis Key调整

原：

```text
chat:history:{userId}
```

改：

```text
chat:history:{sessionId}
```

---

# 当前相关类

```text
AiChatServiceImpl
ChatContextCache
PromptBuilder
```

---

# 新增目录

```text
chat/session
```

---

# 新增类

```text
ChatSession
ChatSessionMapper
ChatSessionService
```

---

# 注意事项

1. 不允许破坏现有聊天接口
2. Redis必须按session隔离
3. PromptBuilder必须支持session上下文
4. Session标题支持自动生成

---

# 完成标准

用户支持：

- 新建聊天
- 删除聊天
- 切换聊天
- 独立上下文
