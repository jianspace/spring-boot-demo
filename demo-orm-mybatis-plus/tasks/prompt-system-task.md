# Task - Prompt模板系统

## 当前问题

当前Prompt：

```text
写死在PromptBuilder
```

无法支持：

- 多角色
- 多Prompt
- 用户画像融合
- 长期记忆

---

# 本次目标

实现：

```text
Prompt模板系统
```

支持：

1. Prompt模板化
2. 用户画像融合
3. 长期记忆融合
4. 多人格扩展

---

# 新增目录

```text
ai/prompt/template
ai/prompt/strategy
```

---

# 新增类

```text
PromptTemplate
PromptStrategy
DefaultPromptStrategy
```

---

# Prompt结构

Prompt必须支持：

```text
system
用户画像
长期记忆
最近上下文
user
```

---

# 当前相关类

```text
PromptBuilder
OpenAiClient
```

---

# 注意事项

禁止：

- Controller拼Prompt
- Service写死Prompt

必须：

统一通过：

```text
PromptBuilder
```

---

# 完成标准

不同用户：

AI支持：

- 不同聊天风格
- 不同人格
- 不同话题倾向
