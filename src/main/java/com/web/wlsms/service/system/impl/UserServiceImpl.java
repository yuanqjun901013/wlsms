package com.web.wlsms.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.TokenDao;
import com.web.wlsms.dao.UserDao;
import com.web.wlsms.entity.TokenEntity;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.system.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    public PageInfo getUserList(SimpleRequest<Integer> request){
        PageHelper.startPage(request.getPage(), request.getRows());
        Map map = new HashMap();
        List<UserEntity> getUserList = userDao.getUserList();
        return new PageInfo<>(getUserList);
    }
}
