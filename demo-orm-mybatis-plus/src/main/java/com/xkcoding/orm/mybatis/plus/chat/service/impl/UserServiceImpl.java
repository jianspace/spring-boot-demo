package com.xkcoding.orm.mybatis.plus.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkcoding.orm.mybatis.plus.chat.entity.User;
import com.xkcoding.orm.mybatis.plus.chat.mapper.UserMapper;
import com.xkcoding.orm.mybatis.plus.chat.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * User Service
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-11-08 18:10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
