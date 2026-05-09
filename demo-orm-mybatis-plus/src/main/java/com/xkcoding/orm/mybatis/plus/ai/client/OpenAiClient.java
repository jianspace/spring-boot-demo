package com.xkcoding.orm.mybatis.plus.ai.client;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.net.InetSocketAddress;
import java.net.Proxy;

@Component
public class OpenAiClient {

    /**
     * OpenAI API Key
     */
    @Value("${openai.api-key}")
    private String apiKey;

    /**
     * OpenAI URL
     */
    @Value("${openai.url}")
    private String apiUrl;

    /**
     * 模型名称
     */
    @Value("${openai.model}")
    private String model;

    /**
     * okhttp client
     */
    private final OkHttpClient client =
        new OkHttpClient.Builder()
//            .proxy(new Proxy(
//                Proxy.Type.HTTP,
//                new InetSocketAddress(
//                    "127.0.0.1",
//                    10809
//                )
//            ))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

 /*   .proxy(new Proxy(
        Proxy.Type.SOCKS,
        new InetSocketAddress(
               "127.0.0.1",
                10808
    )
))*/
    /**
     * AI聊天
     */
    public String chat(String prompt) {

        try {

            // 1. 构建 JSON 请求体
            JSONObject body = new JSONObject();

            body.put("model", model);

            JSONArray messages = new JSONArray();

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", prompt);

            messages.add(userMsg);

            body.put("messages", messages);

            // 2. 创建 RequestBody
            RequestBody requestBody =
                RequestBody.create(
                    MediaType.parse("application/json"),
                    body.toJSONString()
                );

            // 3. 创建 Request
            Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

            // 4. 执行请求
            try (Response response = client.newCall(request).execute()) {

                if (response.body() == null) {
                    return "AI返回为空";
                }

                String result = response.body().string();

                System.out.println("deepSeekAI返回结果：");
                System.out.println(result);

                // 5. 解析JSON
                JSONObject jsonObject =
                    JSON.parseObject(result);

                JSONArray choices =
                    jsonObject.getJSONArray("choices");

                if (choices == null || choices.isEmpty()) {
                    return "AI无返回结果";
                }

                JSONObject message =
                    choices.getJSONObject(0)
                        .getJSONObject("message");

                if (message == null) {
                    return "message为空";
                }

                return message.getString("content");
            }

        } catch (IOException e) {

            e.printStackTrace();

            return "调用OpenAI异常：" + e.getMessage();
        }
    }
}
