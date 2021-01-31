package com.web.wlsms.service.system;

import com.web.wlsms.dao.MessageDao;
import com.web.wlsms.entity.MessageEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service("MessageService")
public class MessageService {

	@Resource
	private MessageDao messageDao;

	public MessageEntity selectMessage(){
		return messageDao.selectMessage();
	}
	public void insertMessage(MessageEntity messageEntity){
		messageDao.insertMessage(messageEntity);
	}

}
