# Task - AI配置中心

## 当前问题

当前系统：

模型配置：

写死：

```text
application.yml
OpenAiClient
```

存在问题：

1. 无法动态切换模型
2. 无法管理temperature
3. 无法管理max_tokens
4. 后期多模型困难

---

# 本次目标

实现：

```text
AI配置中心
```

支持：

1. 模型配置
2. temperature配置
3. max_tokens配置
4. stream配置
5. 后期多模型扩展

---

# 当前相关类

```text
OpenAiClient
PromptBuilder
AiChatServiceImpl
```

---

# 新增目录

```text
ai/config
```

---

# 新增类

```text
AiModelConfig
AiConfigService
AiConfigProperties
```

---

# 当前配置项

```yaml
ai:
  model: deepseek-chat
  temperature: 0.7
  max-tokens: 4096
  stream: true
```

---

# AiModelConfig字段

```java
String model;

Double temperature;

Integer maxTokens;

Boolean stream;
```

---

# OpenAiClient改造

禁止：

写死：

```text
model
temperature
```

必须：

统一从：

```text
AiConfigService
```

读取。

---

# 后续扩展方向

后续支持：

- 多模型切换
- 用户级模型
- 不同角色不同模型
- GPT/Claude/DeepSeek切换

---

# 注意事项

当前阶段：

不做：

```text
后台管理页面
```

仅代码配置。

---

# 完成标准

AI参数：

统一从配置中心读取。
