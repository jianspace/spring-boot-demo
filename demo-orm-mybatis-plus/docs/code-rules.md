#  开发规范

## 一、项目目标

当前项目为：

```text
Spring Boot AI聊天系统
```

项目目标：

1. AI聊天
2. Prompt系统
3. Redis上下文
4. SSE流式输出
5. 后续Agent扩展

---

# 二、技术栈

```text
Java 1.8
Spring Boot 2
MyBatis plus
MySQL
Redis
OkHttp
DeepSeek API
```

---

# 三、代码规范

## 1. 包结构规范

禁止：

```text
util
common
manager
helper
```

过度泛化命名。

必须按业务拆分。

---

推荐：

```text
ai/client
ai/prompt
chat/service
chat/cache
chat/session
```

---

## 2. Controller规范

Controller：

仅负责：

- 接收请求
- 返回响应

禁止：

- 写业务逻辑
- 调Redis
- 拼接Prompt

---

## 3. Service规范

业务逻辑必须放Service。

---

## 4. Redis规范

禁止：

Service直接操作Redis。

必须：

```text
cache层封装
```

例如：

```text
ChatContextCache
```

---

## 5. AI Client规范

所有AI接口：

统一通过：

```text
OpenAiClient
```

禁止：

业务代码直接调用HTTP。

---

## 6. Prompt规范

所有Prompt：

统一放：

```text
ai/prompt
```

禁止：

业务代码写死Prompt。

---

## 7. DTO规范

请求参数：

```text
xxxRequest
```

响应参数：

```text
xxxResponse
```

---

## 8. Mapper规范

Mapper：

仅允许数据库操作。

禁止：

业务逻辑。

---

## 9. 异常处理规范

禁止：

```java
e.printStackTrace()
```

必须：

统一日志。

---

## 10. 日志规范

使用：

```java
@Slf4j
```

---

# 四、开发原则

## 1. 小步迭代

一次只实现一个功能。

禁止：

一次生成整个系统。

---

## 2. 不允许破坏现有功能

新增功能：

必须兼容现有接口。

---

## 3. 优先复用已有代码

禁止：

重复实现已有逻辑。

---

## 4. 保持目录清晰

新增模块：

必须放入对应业务目录。

---

# 五、AI开发规范（重要）

Cursor生成代码时：

1. 优先修改已有类
2. 不随意创建新目录
3. 不允许生成废弃代码
4. 不允许生成未使用类
5. 不允许重复DTO
6. 保持代码可读性

---

# 六、当前阶段开发重点

当前优先：

1. Prompt系统
2. 会话系统
3. Token统计
4. 上下文裁剪

当前不开发：

- OpenClaw
- 自动聊天
- 多Agent
- 工作流编排

避免系统复杂度失控。

---

# 七、代码风格

要求：

- 方法单一职责
- 类不要过大
- 命名清晰
- 避免过度封装
- 避免过度设计

---

# 八、当前架构原则

当前项目：

优先：

```text
简单稳定
```

不是：

```text
复杂炫技
```

---
#九、修改或新增文件规则

要求：

- 新增文件，需标注新增日期，需求概要or类功能模块
- 修改文件，需标注修改日期，需求概要or类功能模块，以start/end标注修改范围
---

# 十、执行方式

执行开发任务时：

1. 先阅读docs,rules,skills,tasks目录下文件
2. 按指定需求文档逐步实现
3. 不允许一次性重构整个项目
4. 每次只改一个模块
5. 保持系统可运行
