package com.web.wlsms.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MessageDao;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


	public PageInfo getOperationList(SimpleRequest<String> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		List<MessageEntity> list = messageDao.getOperationList(request.getRequest());
		return new PageInfo<>(list);
	}

	public BaseResponse feedbackSubmit(MessageEntity messageEntity){
		int num = messageDao.feedbackSubmit(messageEntity);
		if(num > 0){
			return BaseResponse.ok("反馈成功");

		}else {
			return BaseResponse.fail("反馈失败");
		}
	}

}
