package com.web.wlsms.service.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.request.SimpleRequest;

public interface UserService {
    UserEntity selectUserById(String userNo);
    int editUserByUserNo(UserEntity userEntity);
    int editUserPwd(UserEntity userEntity);
    PageInfo getUserList(SimpleRequest<Integer> request);
}
