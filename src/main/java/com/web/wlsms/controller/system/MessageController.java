package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"消息管理"})
@RestController
@RequestMapping("/admin/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    /**
     * message推送状态用于右边栏
     * @return
     */
    @ApiOperation("message推送状态用于右边栏")
    @PostMapping("getMessageList")
    public List<MessageEntity> getMessageList(){
        List<MessageEntity> selectMessage = messageService.selectMessage();
        if(null != selectMessage && selectMessage.size()>0){
            for (MessageEntity messageEntity:selectMessage){
                messageEntity.setContent(messageEntity.getUserNo()+ "  "+ messageEntity.getBuildTime() +"  " + messageEntity.getTitle());
            }
        }
        return selectMessage;
    }

    /**
     * 查看所有审计状态
     * @param params
     * @return
     */
    @ApiOperation("查看所有审计状态")
    @PostMapping("getOperationList")
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

    @ApiOperation("异常反馈")
    @PostMapping("feedbackSubmit")
    public BaseResponse feedbackSubmit(HttpServletRequest request, MessageEntity messageEntity){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == messageEntity){
            return BaseResponse.fail("入参有误");
        }
        if(StringUtils.isNotBlank(userNo)){
            messageEntity.setFeedbackUser(userNo);
        }
        return messageService.feedbackSubmit(messageEntity);
    }
}
