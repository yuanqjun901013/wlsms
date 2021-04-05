package com.web.wlsms.service.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.request.SimpleRequest;

import java.util.List;

public interface UserService {
    UserEntity selectUserById(String userNo);
    int editUserByUserNo(UserEntity userEntity);
    int editUserPwd(UserEntity userEntity);
    int saveUser(UserEntity userEntity);
    PageInfo getUserList(SimpleRequest<String> request);
    int deleteUser(UserEntity userEntity);
    List<UserEntity> getUserArr();
}
