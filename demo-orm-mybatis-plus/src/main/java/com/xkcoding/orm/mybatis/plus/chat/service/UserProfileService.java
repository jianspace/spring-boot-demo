package com.xkcoding.orm.mybatis.plus.chat.service;

import com.xkcoding.orm.mybatis.plus.chat.domain.UserProfile;

public interface UserProfileService {

    UserProfile getProfile(Long userId);
}
