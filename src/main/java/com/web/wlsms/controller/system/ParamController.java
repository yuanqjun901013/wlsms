package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.system.MessageService;
import com.web.wlsms.service.system.ParamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/param")
public class ParamController {
    @Resource
    private ParamService paramService;

    @RequestMapping("getParametersList")
    public Map<String,Object> getParametersList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getParametersList = paramService.getParametersList(params);
            resultMap.put("total", getParametersList.getTotal());
            resultMap.put("rows", getParametersList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
}
