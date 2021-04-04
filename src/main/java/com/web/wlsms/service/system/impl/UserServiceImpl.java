package com.web.wlsms.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.RoleUserDao;
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

    @Resource
    private RoleUserDao roleUserDao;

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
    public PageInfo getUserList(SimpleRequest<String> request){
        PageHelper.startPage(request.getPage(), request.getRows());
        Map map = new HashMap();
        map.put("keyWord",request.getRequest());
        List<UserEntity> getUserList = userDao.getUserList(map);
        return new PageInfo<>(getUserList);
    }

    @Override
    public int saveUser(UserEntity userEntity){
        return userDao.saveUser(userEntity);
    }

    @Override
    public int deleteUser(UserEntity userEntity){
       UserEntity userInfo = userDao.selectUserById(userEntity.getId());
       if(null == userInfo){
           return 0;
       }
       int userNum = userDao.deleteUser(userEntity);
       if(userNum >0){
           roleUserDao.deleteUserRoleByUserNo(userInfo.getUserNo());
       }
       return userNum;
    }
}
