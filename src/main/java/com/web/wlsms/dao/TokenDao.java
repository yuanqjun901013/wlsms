package com.web.wlsms.dao;

import com.web.wlsms.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenDao {
    TokenEntity findByUserNo(String userNo);

    void insertUserToken(TokenEntity tokenEntity);

    void updateUserToken(TokenEntity tokenEntity);

    void deleteUserToken(String userNo);

}
