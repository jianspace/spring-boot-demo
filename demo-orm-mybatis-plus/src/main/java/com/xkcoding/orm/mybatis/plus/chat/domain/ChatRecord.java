package com.xkcoding.orm.mybatis.plus.chat.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("chat_record")
public class ChatRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    /**
     * user / assistant
     */
    private String role;

    /**
     * 聊天内容
     */
    private String message;

    /**
     * prompt_tokens
     */
    private Integer promptTokens;

    /**
     * completion_tokens
     */
    private Integer completionTokens;

    /**
     * total_tokens
     */
    private Integer totalTokens;

    @TableField(fill = INSERT)
    private Date createTime;
}