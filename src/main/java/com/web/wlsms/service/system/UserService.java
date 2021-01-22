package com.web.wlsms.service.system;

import com.web.wlsms.entity.UserEntity;

public interface UserService {
    UserEntity selectUserById(String userNo);
}
