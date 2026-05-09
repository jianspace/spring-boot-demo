package com.xkcoding.orm.mybatis.plus.chat.dto;


import lombok.Data;

@Data
public class ChatRequest {

    private Long userId;

    private String message;
}
