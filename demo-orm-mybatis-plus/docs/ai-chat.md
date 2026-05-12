# Phase1 - AI聊天系统升级

## 一、阶段目标

当前系统已经完成：

- Spring Boot AI接口调用
- DeepSeek API联调
- OkHttp请求
- MySQL聊天记录存储
- 基础多轮对话

本阶段目标：

1. Redis聊天上下文缓存
2. SSE流式输出
3. 优化AI聊天体验
4. 为后续Agent系统做基础

---

# 二、当前项目结构

```text
com.example.demo
├── ai
│   ├── client
│   ├── dto
│   └── prompt
│
├── chat
│   ├── controller
│   ├── service
│   ├── mapper
│   ├── domain
│   └── dto
```

---

# 三、Phase1整体架构

```text
用户请求
    ↓
Controller
    ↓
ChatService
    ↓
Redis上下文缓存
    ↓
PromptBuilder
    ↓
OpenAiClient
    ↓
DeepSeek API
    ↓
SSE流式输出
    ↓
用户前端
```

---

# 四、Redis聊天上下文缓存

## 目标

减少频繁查询MySQL。

最近聊天记录优先使用Redis。

MySQL仅作为长期存储。

---

## Redis Key设计

```text
chat:history:{userId}
```

例如：

```text
chat:history:1
```

---

## Redis Value设计

使用JSON数组：

```json
[
  {
    "role":"user",
    "content":"你好"
  },
  {
    "role":"assistant",
    "content":"你好呀"
  }
]
```

---

## 上下文策略

仅保留最近：

```text
20条消息
```

原因：

- 避免token过长
- 提高AI响应速度
- 降低API成本

---

## Redis流程

### 用户发送消息

```text
1. 查询Redis上下文
2. Redis不存在：
      查询MySQL
      回填Redis
3. 拼接Prompt
4. 调用AI
5. 用户消息写Redis
6. AI消息写Redis
7. 异步保存MySQL
```

---

# 五、Redis模块设计

新增：

```text
chat/cache
```

新增类：

```text
ChatContextCache.java
```

职责：

- 获取上下文
- 添加上下文
- 清理上下文

---

## ChatContextCache接口设计

```java
List<ChatMessage> getContext(Long userId);

void append(Long userId,
            ChatMessage message);

void clear(Long userId);
```

---

# 六、SSE流式输出

## 目标

实现类似ChatGPT的：

```text
边生成边输出
```

而不是：

```text
等待全部生成后一次性返回
```

---

# 七、SSE技术方案

使用：

```text
SseEmitter
```

实现服务端流式推送。

---

# 八、AI流式输出流程

```text
用户请求
    ↓
Controller创建SseEmitter
    ↓
AiStreamService
    ↓
OpenAiClient(stream=true)
    ↓
读取AI chunk
    ↓
emitter.send()
    ↓
前端实时显示
```

---

# 九、SSE模块设计

新增：

```text
chat/sse
```

新增类：

```text
AiStreamService.java
```

职责：

- AI流式输出
- chunk解析
- SSE推送

---

# 十、OpenAiClient升级

新增：

```java
streamChat(...)
```

用于：

```text
stream=true
```

流式请求。

---

# 十一、开发步骤（Cursor执行）

## Step1

实现Redis配置。

包括：

- Redis依赖
- RedisConfig
- 序列化配置

---

## Step2

实现ChatContextCache。

包括：

- 获取上下文
- 添加上下文
- 限制最多20条

---

## Step3

改造AiChatService。

实现：

- Redis优先
- MySQL兜底
- Redis回填

---

## Step4

实现SSE Controller。

返回：

```java
SseEmitter
```

---

## Step5

实现DeepSeek stream=true。

---

## Step6

实现AI chunk解析。

---

## Step7

实现前端实时输出。

---

# 十二、当前阶段完成标准

完成后系统支持：

- AI连续对话
- Redis上下文缓存
- MySQL长期存储
- SSE流式输出
- 类似ChatGPT的聊天体验

---

# 十三、当前阶段暂不开发

本阶段不开发：

- Agent系统
- OpenClaw自动化
- 多人格
- 自动聊天
- 工作流编排

避免项目复杂度过高。

---

# 十四、下一阶段规划（Phase2）

Phase2预计开发：

1. Prompt系统
2. 多角色人格
3. Token统计
4. 上下文裁剪
5. 用户画像系统
