package com.xkcoding.orm.mybatis.plus.chat.memory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import com.xkcoding.orm.mybatis.plus.chat.mapper.ChatRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 长期记忆服务
 * 版本: 1.0
 * 创建日期: 2026-05-12
 * 功能: 从聊天历史生成长期记忆摘要
 */

import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Service
public class LongTermMemoryService {

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    public String summarize(Long userId) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
            .orderByDesc("create_time")
            .last("limit 20");

        List<ChatRecord> records = chatRecordMapper.selectList(queryWrapper);
        if (records == null || records.isEmpty()) {
            return "暂无长期记忆。";
        }

        StringJoiner summary = new StringJoiner("；");
        int count = Math.min(records.size(), 6);
        for (int i = 0; i < count; i++) {
            ChatRecord record = records.get(i);
            if ("user".equals(record.getRole())) {
                summary.add("用户说：" + trimText(record.getMessage()));
            } else if ("assistant".equals(record.getRole())) {
                summary.add("助手答：" + trimText(record.getMessage()));
            }
        }

        return summary.toString();
    }

    private String trimText(String text) {
        if (text == null) {
            return "";
        }
        text = text.trim();
        return text.length() > 30 ? text.substring(0, 30) + "..." : text;
    }
}
