# Token Management Skill

## 当前项目Token原则

Token：

是AI系统核心成本。

必须：

- 统计
- 控制
- 裁剪

---

# 当前Token来源

统一从：

```text
AI响应usage字段
```

读取。

禁止：

业务层手动计算。

---

# 当前项目Token用途

1. 成本分析
2. 用户统计
3. 上下文裁剪
4. 后期限流

---

# 当前Token字段

```text
prompt_tokens
completion_tokens
total_tokens
```

---

# 上下文控制原则

禁止：

无限拼接聊天记录。

必须：

动态裁剪。

---

# 当前裁剪策略

优先保留：

1. system
2. 用户画像
3. 长期记忆
4. 最近聊天

---

# 当前推荐Token估算

初期：

```text
字符数 / 4
```

后期：

接专业Tokenizer。
