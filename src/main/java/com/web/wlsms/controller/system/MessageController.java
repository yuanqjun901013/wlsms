package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.system.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @RequestMapping("getMessageList")
    public List<MessageEntity> getMessageList(){
        List<MessageEntity> selectMessage = messageService.selectMessage();
        if(null != selectMessage && selectMessage.size()>0){
            for (MessageEntity messageEntity:selectMessage){
                messageEntity.setContent(messageEntity.getUserNo()+ "  " + messageEntity.getTitle());
            }
        }
        return selectMessage;
    }

    @RequestMapping("getOperationList")
    public Map<String,Object> getOperationList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getOperationList = messageService.getOperationList(params);
            resultMap.put("total", getOperationList.getTotal());
            resultMap.put("rows", getOperationList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
}
