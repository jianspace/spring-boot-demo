# Prompt Engineering Skill

## 当前项目Prompt目标

Prompt负责：

1. AI人格
2. AI行为
3. AI聊天风格
4. AI长期记忆融合

---

# 当前Prompt结构

```text
system
用户画像
长期记忆
最近上下文
user
```

---

# Prompt原则

1. 简洁
2. 明确
3. 约束清晰
4. 避免超长

---

# 用户画像融合

Prompt必须支持：

```text
personality
speaking_style
interests
ai_summary
```

---

# 长期记忆原则

长期记忆：

只保留：

```text
高价值用户信息
```

禁止：

无限增长。

---

# 当前Prompt入口

统一：

```text
PromptBuilder
```

禁止：

业务代码拼接Prompt。

---

# 当前项目支持角色

当前：

```text
默认AI助手
```

后续：

```text
AI客服
AI销售
AI陪伴
AI技术顾问
```
