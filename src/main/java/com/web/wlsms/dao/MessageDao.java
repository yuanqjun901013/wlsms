package com.web.wlsms.dao;

import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao {

   List<MessageEntity> selectMessageList();
   List<MessageEntity> getOperationList(String keyWord);
   void insertMessage(MessageEntity messageEntity);
   int feedbackSubmit(MessageEntity messageEntity);
}
