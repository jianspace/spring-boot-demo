# Project Structure

## 一、当前项目定位

当前项目：

```text
Spring Boot AI聊天系统
```

目标：

1. AI聊天
2. Prompt工程
3. 用户画像
4. 长期记忆
5. SSE流式输出
6. 后续Agent扩展

---

# 二、核心原则

项目：

采用：

```text
按业务模块拆分
```

禁止：

```text
所有代码堆一起
```

---

# 三、项目结构

```text
com.example.demo
│
├── ai
├── chat
├── user
├── common
```

---

# 四、ai模块

负责：

```text
AI能力
```

包括：

- AI调用
- Prompt
- Token管理
- AI配置
- 上下文裁剪

---

## ai目录结构

```text
ai
├── client
├── config
├── context
├── dto
├── prompt
│   ├── builder
│   ├── strategy
│   └── template
```

---

# ai/client

负责：

```text
AI接口调用
```

例如：

```text
OpenAiClient
```

禁止：

业务代码直接HTTP请求。

---

# ai/config

负责：

```text
AI配置中心
```

例如：

```text
AiConfigService
AiModelConfig
```

---

# ai/context

负责：

```text
上下文裁剪
Token估算
```

例如：

```text
ContextTrimmer
TokenEstimator
```

---

# ai/dto

负责：

```text
AI请求响应对象
```

例如：

```text
ChatCompletionResult
ChatMessage
```

---

# ai/prompt

负责：

```text
Prompt工程
```

禁止：

业务代码拼Prompt。

---

# 五、chat模块

负责：

```text
聊天业务
```

包括：

- 聊天记录
- SSE
- Redis缓存
- Session管理
- 长期记忆

---

# chat目录结构

```text
chat
├── cache
├── controller
├── domain
├── dto
├── mapper
├── memory
├── service
├── session
├── sse
```

---

# chat/cache

负责：

```text
Redis缓存
```

例如：

```text
ChatContextCache
```

禁止：

Service直接操作Redis。

---

# chat/controller

负责：

```text
接口层
```

仅允许：

- 接收请求
- 返回响应

禁止：

- 业务逻辑
- Prompt拼接
- Redis操作

---

# chat/domain

负责：

```text
数据库实体
```

例如：

```text
ChatRecord
ChatSession
```

---

# chat/dto

负责：

```text
聊天请求响应对象
```

例如：

```text
ChatRequest
ChatResponse
```

---

# chat/mapper

负责：

```text
数据库操作
```

禁止：

业务逻辑。

---

# chat/memory

负责：

```text
长期记忆
AI摘要
```

例如：

```text
LongTermMemoryService
```

---

# chat/service

负责：

```text
聊天业务逻辑
```

例如：

```text
AiChatService
```

---

# chat/session

负责：

```text
多会话管理
```

例如：

```text
ChatSessionService
```

---

# chat/sse

负责：

```text
SSE流式输出
```

例如：

```text
AiStreamService
```

---

# 六、user模块

负责：

```text
用户相关能力
```

包括：

- 用户画像
- 用户信息
- 用户偏好

---

# user目录结构

```text
user
├── profile
```

---

# user/profile

负责：

```text
用户画像
```

例如：

```text
UserProfile
UserProfileService
```

---

# 七、common模块

负责：

```text
公共能力
```

仅允许：

- 通用异常
- 通用响应
- 工具类

禁止：

业务逻辑。

---

# common目录结构

```text
common
├── config
├── exception
├── response
├── util
```

---

# 八、严格禁止事项

## 禁止

```text
util包放业务逻辑
```

---

## 禁止

```text
Controller写业务代码
```

---

## 禁止

```text
Service直接操作Redis
```

---

## 禁止

```text
业务代码直接HTTP调用AI
```

---

## 禁止

```text
业务代码拼接Prompt
```

---

# 九、AI生成代码规则（重要）

Cursor/Codex生成代码时：

必须：

1. 优先复用已有目录
2. 优先修改已有类
3. 不随意创建新模块
4. 不允许重复DTO
5. 不允许生成废弃代码

---

# 十、当前阶段核心目录

当前开发重点：

```text
ai/prompt
chat/session
chat/cache
chat/memory
user/profile
```

---

# 十一、当前架构原则

当前项目：

优先：

```text
简单稳定
```

不是：

```text
复杂抽象
```

---

# 十二、新增功能放置原则

## AI能力

放：

```text
ai/*
```

---

## 聊天业务

放：

```text
chat/*
```

---

## 用户画像

放：

```text
user/*
```

---

## 公共能力

放：

```text
common/*
```

---

# 十三、Cursor执行流程

Cursor开发前：

必须：

1. 阅读 project-structure.md
2. 阅读 code-rules.md
3. 阅读当前task文档
4. 按模块生成代码

禁止：

无结构随意生成代码。
