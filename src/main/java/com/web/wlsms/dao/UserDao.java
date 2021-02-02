package com.web.wlsms.dao;

import com.web.wlsms.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    UserEntity selectUserByNo(String userNo);
    int editUserByUserNo(UserEntity userEntity);
    int editUserPwd(UserEntity userEntity);
    List<UserEntity> getUserList();
}
