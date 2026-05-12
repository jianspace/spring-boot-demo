package com.xkcoding.orm.mybatis.plus.ai.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import com.xkcoding.orm.mybatis.plus.ai.dto.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OpenAI API客户端
 * 版本: 1.1
 * 修改日期: 2026-05-12
 * 修改内容: 添加日志规范，替换println和printStackTrace
 */
@Slf4j
@Component
public class OpenAiClient {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final OkHttpClient client =
        new OkHttpClient.Builder()

            // V2Ray HTTP代理
            .proxy(new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress(
                    "127.0.0.1",
                    10809
                )
            ))

            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * AI聊天
     */
public ChatResponse chat(List<ChatMessage> messages) {

        try {
            JSONObject body = new JSONObject();
            body.put("model", model);

            JSONArray messageArray = new JSONArray();
            for (ChatMessage msg : messages) {
                JSONObject item = new JSONObject();
                item.put("role", msg.getRole());
                item.put("content", msg.getContent());
                messageArray.add(item);
            }

            body.put("messages", messageArray);

            RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                body.toJSONString()
            );

            Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.body() == null) {
                    return ChatResponse.builder()
                        .content("AI返回为空")
                        .promptTokens(0)
                        .completionTokens(0)
                        .totalTokens(0)
                        .build();
                }

                String result = response.body().string();
                log.debug("OpenAI返回结果: {}", result);

                JSONObject jsonObject = JSON.parseObject(result);
                JSONArray choices = jsonObject.getJSONArray("choices");

                if (choices == null || choices.isEmpty()) {
                    return ChatResponse.builder()
                        .content("AI无返回")
                        .promptTokens(0)
                        .completionTokens(0)
                        .totalTokens(0)
                        .build();
                }

                JSONObject usage = jsonObject.getJSONObject("usage");
                Integer promptTokens = usage == null ? 0 : usage.getInteger("prompt_tokens");
                Integer completionTokens = usage == null ? 0 : usage.getInteger("completion_tokens");
                Integer totalTokens = usage == null ? 0 : usage.getInteger("total_tokens");

                JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                return ChatResponse.builder()
                    .content(message.getString("content"))
                    .promptTokens(promptTokens)
                    .completionTokens(completionTokens)
                    .totalTokens(totalTokens)
                    .build();
            }

        } catch (IOException e) {
            log.error("AI调用异常", e);
            return ChatResponse.builder()
                .content("AI调用异常：" + e.getMessage())
                .promptTokens(0)
                .completionTokens(0)
                .totalTokens(0)
                .build();
        }
    }
}
