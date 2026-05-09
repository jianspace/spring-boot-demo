package com.xkcoding.orm.mybatis.plus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkcoding.orm.mybatis.plus.chat.domain.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

    /*void insert(ChatRecord record);

    List<ChatRecord> selectRecentByUserId(@Param("userId") Long userId);*/
}
