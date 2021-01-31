package com.web.wlsms.dao;

import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao {

   MessageEntity selectMessage();
   void insertMessage(MessageEntity messageEntity);
}
