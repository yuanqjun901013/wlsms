package com.web.wlsms.service.system.impl;

import com.web.wlsms.dao.TokenDao;
import com.web.wlsms.dao.UserDao;
import com.web.wlsms.entity.TokenEntity;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.service.system.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public UserEntity selectUserById(String userNo){
        return userDao.selectUserByNo(userNo);
    }

    @Override
    public int editUserByUserNo(UserEntity userEntity){
        return userDao.editUserByUserNo(userEntity);
    }

    @Override
    public int editUserPwd(UserEntity userEntity){
        return userDao.editUserPwd(userEntity);
    }
}
