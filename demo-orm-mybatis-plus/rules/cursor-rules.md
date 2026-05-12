# Cursor开发规范（Phase2）

## 一、当前项目目标

当前项目为：

```text
Spring Boot AI聊天系统
```

当前重点：

1. Prompt系统
2. 用户画像
3. Session系统
4. 长期记忆
5. Token统计

---

# 二、Session规范

所有聊天：

必须基于：

```text
sessionId
```

禁止：

```text
userId直接作为聊天上下文
```

---

# 三、Prompt规范

Prompt：

必须：

- 模板化
- 可扩展
- 可复用

禁止：

- 业务代码写死Prompt
- Controller拼接Prompt

---

# 四、用户画像规范

用户画像：

采用：

```text
轻量标签化
```

禁止：

- 复杂心理模型
- 过度字段化
- 向量数据库

---

# 五、长期记忆规范

长期记忆：

统一使用：

```text
ai_summary
```

禁止：

无限拼接聊天记录。

---

# 六、Redis规范

Redis仅保存：

```text
短期上下文
```

长期数据：

必须存MySQL。

---

# 七、Token规范

所有AI响应：

必须记录：

```text
prompt_tokens
completion_tokens
total_tokens
```

---

# 八、代码规范

禁止：

```text
util
helper
manager
```

泛化命名。

---

# 九、AI Client规范

所有AI调用：

统一通过：

```text
OpenAiClient
```

禁止：

业务代码直接HTTP调用。

---

# 十、开发原则

优先：

```text
简单稳定
```

不是：

```text
复杂炫技
```

---

# 十一、Cursor执行原则

1. 先阅读docs
2. 按task逐步实现
3. 不允许一次性重构
4. 每次只改一个模块
5. 保持系统可运行
