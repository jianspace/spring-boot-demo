# User Memory Skill

## 当前项目记忆结构

采用：

```text
短期记忆 + 长期记忆
```

---

# 短期记忆

Redis：

```text
最近聊天上下文
```

特点：

- 热数据
- 高频访问
- TTL 30分钟

---

# 长期记忆

MySQL：

```text
用户画像
AI摘要
```

特点：

- 长期保存
- Prompt融合
- 个性化聊天

---

# 当前长期记忆字段

```text
personality
speaking_style
interests
ai_summary
```

---

# AI摘要原则

AI摘要：

只保留：

```text
长期稳定特征
```

例如：

- 长期兴趣
- 性格特点
- 聊天偏好
- 长期目标

---

# 禁止事项

禁止：

- 无限聊天记录拼接
- 所有信息长期存储
- 复杂心理分析

---

# 当前项目记忆目标

实现：

```text
AI像“认识用户”
```

而不是：

```text
单次问答机器人
```
