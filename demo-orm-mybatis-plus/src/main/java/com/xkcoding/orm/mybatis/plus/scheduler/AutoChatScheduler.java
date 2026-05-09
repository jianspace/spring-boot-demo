package com.xkcoding.orm.mybatis.plus.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoChatScheduler {

    @Scheduled(fixedDelay = 60000)
    public void autoChat() {

        System.out.println("自动聊天任务执行");

        // TODO:
        // 读取待回复用户
        // 调用AI
        // 自动发送
    }
}
