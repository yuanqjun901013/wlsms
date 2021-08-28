package com.web.wlsms.controller.mongodb;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.mongodb.MongoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mongo/config")
public class MongoConfigController {
    @Resource
    private MongoService mongoService;
    @RequestMapping("saveMongoConfig")
    public BaseResponse saveMongoConfig(WlsmsMongodbConf wlsmsMongodbConf){
        if(null == wlsmsMongodbConf){
            return BaseResponse.fail("入参有误，请重试");
        }
        return mongoService.saveMongoConfig(wlsmsMongodbConf);
    }

    @RequestMapping("getMongoDbList")
    public Map<String,Object> getMongoDbList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getMongoDbList = mongoService.getMongoDbList(params);
            resultMap.put("total", getMongoDbList.getTotal());
            resultMap.put("rows", getMongoDbList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
}
