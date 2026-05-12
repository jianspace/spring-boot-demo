# Redis Skill

## 当前项目Redis用途

1. 聊天上下文缓存
2. Session缓存
3. 用户状态缓存

---

# Redis Key规范

聊天上下文：

```text
chat:history:{sessionId}
```

---

# TTL规范

聊天上下文：

```text
30分钟
```

---

# Redis原则

1. Redis只保存热数据
2. MySQL负责永久存储
3. Redis仅保存最近上下文
4. Redis禁止无限增长

---

# Redis访问规范

禁止：

Service直接操作Redis。

必须：

统一通过：

```text
ChatContextCache
```

---

# 上下文策略

当前：

```text
固定20条
```

后续：

```text
按Token裁剪
```
