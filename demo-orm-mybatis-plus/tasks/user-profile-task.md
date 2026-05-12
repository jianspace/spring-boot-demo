# Task - 用户画像系统

## 当前问题

当前AI：

无法记住用户特点。

导致：

- 回复缺乏个性
- 话题延续性差
- 无法形成长期陪伴体验

---

# 本次目标

实现：

```text
轻量用户画像系统
```

支持：

1. 用户兴趣
2. 用户性格
3. 用户聊天风格
4. AI长期记忆摘要

---

# 数据表设计

新增：

```text
user_profile
```

字段：

```text
id
user_id
nickname
gender
personality
speaking_style
interests
ai_summary
create_time
update_time
```

---

# 字段说明

## personality

例如：

```text
理性型
感性型
焦虑型
外向型
```

---

## speaking_style

例如：

```text
直接
温柔
幽默
技术向
```

---

## interests

例如：

```text
AI
股票
汽车
创业
```

---

## ai_summary

AI长期记忆摘要。

例如：

```text
用户长期关注AI开发，
希望通过技术改变人生，
思绪容易跳跃。
```

---

# 当前相关类

```text
PromptBuilder
AiChatServiceImpl
```

---

# 新增目录

```text
user/profile
```

---

# 新增类

```text
UserProfile
UserProfileMapper
UserProfileService
```

---

# Prompt升级要求

Prompt必须支持：

```text
用户画像 + 最近上下文
```

---

# 注意事项

1. 不允许过度字段化
2. 用户画像保持轻量
3. AI摘要优先
4. 不做复杂心理模型
5. 不引入向量数据库

---

# 完成标准

AI支持：

- 记住用户兴趣
- 根据性格调整聊天风格
- 根据长期记忆延续话题
