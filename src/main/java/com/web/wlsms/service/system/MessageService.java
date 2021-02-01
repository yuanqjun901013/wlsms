package com.web.wlsms.service.system;

import com.web.wlsms.dao.MessageDao;
import com.web.wlsms.entity.MessageEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service("MessageService")
public class MessageService {

	@Resource
	private MessageDao messageDao;
	public List<MessageEntity> selectMessage(){
		return messageDao.selectMessageList();
	}
	public void insertMessage(MessageEntity messageEntity){
		messageDao.insertMessage(messageEntity);
	}

}
