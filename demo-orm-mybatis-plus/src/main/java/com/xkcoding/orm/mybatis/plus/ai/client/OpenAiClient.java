package com.xkcoding.orm.mybatis.plus.ai.client;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;


import com.xkcoding.orm.mybatis.plus.ai.dto.ChatMessage;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public String chat(List<ChatMessage> messages) {

        try {

            JSONObject body = new JSONObject();

            body.put("model", model);

            JSONArray messageArray =
                new JSONArray();

            for (ChatMessage msg : messages) {

                JSONObject item =
                    new JSONObject();

                item.put("role", msg.getRole());
                item.put("content", msg.getContent());

                messageArray.add(item);
            }

            body.put("messages", messageArray);

            // 2. 创建 RequestBody
            RequestBody requestBody =
                RequestBody.create(
                    MediaType.parse("application/json"),
                    body.toJSONString()
                );

            Request request =
                new Request.Builder()
                    .url(apiUrl)
                    .post(requestBody)
                    .addHeader(
                        "Authorization",
                        "Bearer " + apiKey
                    )
                    .addHeader(
                        "Content-Type",
                        "application/json"
                    )
                    .build();

            try (Response response =
                     client.newCall(request).execute()) {

                if (response.body() == null) {
                    return "AI返回为空";
                }

                String result =
                    response.body().string();

                System.out.println("OpenAI返回结果：");
                System.out.println(result);

                JSONObject jsonObject =
                    JSON.parseObject(result);

                JSONArray choices =
                    jsonObject.getJSONArray("choices");

                if (choices == null ||
                    choices.isEmpty()) {

                    return "AI无返回";
                }

                JSONObject message =
                    choices.getJSONObject(0)
                        .getJSONObject("message");

                return message.getString("content");
            }

        } catch (IOException e) {

            e.printStackTrace();

            return "AI调用异常：" + e.getMessage();
        }
    }
}
