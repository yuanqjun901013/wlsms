package com.web.wlsms.dao;

import com.web.wlsms.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    UserEntity selectUserByNo(String userNo);
    int editUserByUserNo(UserEntity userEntity);
    int editUserPwd(UserEntity userEntity);
    int saveUser(UserEntity userEntity);
    List<UserEntity> getUserList(Map map);
    int deleteUser(UserEntity userEntity);
    UserEntity selectUserById(long id);
    UserEntity selectUserByUserNo(String userNo);
}
