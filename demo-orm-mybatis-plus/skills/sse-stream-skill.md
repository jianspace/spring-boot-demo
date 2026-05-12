# SSE Stream Skill

## 当前项目SSE用途

实现：

```text
AI流式输出
```

---

# 当前技术方案

使用：

```text
SseEmitter
```

---

# SSE原则

1. 单向推送
2. 适合AI文本流
3. 不使用WebSocket

---

# AI流式输出流程

```text
AI chunk
    ↓
OpenAiClient
    ↓
AiStreamService
    ↓
SseEmitter.send()
```

---

# SSE注意事项

1. emitter超时处理
2. emitter异常关闭
3. chunk结束处理
4. AI异常处理

---

# 当前相关类

```text
AiStreamService
OpenAiClient
```
