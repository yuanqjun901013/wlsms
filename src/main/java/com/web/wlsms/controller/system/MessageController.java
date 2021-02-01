package com.web.wlsms.controller.system;

import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.service.system.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @RequestMapping("getMessageList")
    public List<String> getMessageList(){
        List<String> messageList = new ArrayList<>();
        List<MessageEntity> selectMessage = messageService.selectMessage();
        if(null != selectMessage && selectMessage.size() > 0){
          for (MessageEntity messageEntity:selectMessage){
              messageList.add("用户:"+messageEntity.getContent()+ messageEntity.getTitle() + "  " + messageEntity.getBuildTime());
          }
        }
        return messageList;
    }
}
