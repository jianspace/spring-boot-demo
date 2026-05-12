# Task - 上下文裁剪系统

## 当前问题

当前系统：

固定保留：

```text
最近20条消息
```

存在问题：

1. Token可能超限
2. 不同消息长度差异巨大
3. 长文本容易导致AI报错
4. 无法适配不同模型

---

# 本次目标

实现：

```text
基于Token的上下文裁剪
```

---

# 当前相关类

```text
PromptBuilder
ChatContextCache
AiChatServiceImpl
```

---

# 当前策略

当前：

```text
固定20条
```

---

# 新策略

改为：

```text
基于Token动态裁剪
```

---

# 裁剪原则

优先保留：

1. system prompt
2. 用户画像
3. 长期记忆
4. 最近聊天

---

# 裁剪流程

```text
system
    ↓
用户画像
    ↓
长期记忆
    ↓
最近聊天
```

超过：

```text
max_context_tokens
```

则：

从最旧聊天开始裁剪。

---

# 新增配置

application.yml：

```yaml
ai:
  context:
    max-tokens: 8000
```

---

# 新增模块

```text
ai/context
```

---

# 新增类

```text
ContextTrimmer
TokenEstimator
```

---

# TokenEstimator职责

负责：

估算：

```text
message token
```

---

# 注意事项

1. 不要求绝对精准
2. 允许粗略估算
3. 后期再接专业Tokenizer
4. 不允许无限上下文

---

# 当前推荐策略

初期：

```text
字符数 / 4
```

近似Token。

---

# 后续扩展

后续支持：

- 不同模型不同context
- 智能摘要裁剪
- 长期记忆压缩

---

# 完成标准

AI聊天：

不会因为：

```text
context too long
```

报错。
