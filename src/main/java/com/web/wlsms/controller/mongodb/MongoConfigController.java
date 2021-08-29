package com.web.wlsms.controller.mongodb;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.mongodb.MongoService;
import org.apache.commons.lang3.StringUtils;
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
        if(null == wlsmsMongodbConf){
            return BaseResponse.fail("参数异常");
        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongodbIp())){
//            return BaseResponse.fail("数据库ip为空");
//        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongodbDatabase())){
//            return BaseResponse.fail("数据库名为空");
//        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getCollectionName())){
            return BaseResponse.fail("collection集合为空");
        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongoUser())){
//            return BaseResponse.fail("用户名为空");
//        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongoPwd())){
//            return BaseResponse.fail("密码为空");
//        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getWxName())){
            return BaseResponse.fail("系统名称为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getZplValue())){
            return BaseResponse.fail("中频为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getDplValue())){
            return BaseResponse.fail("电平为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getTkplValue())){
            return BaseResponse.fail("天空频率为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getMslValue())){
            return BaseResponse.fail("码速率为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getBuildTime())){
            return BaseResponse.fail("采集时间为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getZzbValue())){
            return BaseResponse.fail("载噪比为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getTzysName())){
            return BaseResponse.fail("调制样式为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getBmType())){
            return BaseResponse.fail("编码类型为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getMlName())){
            return BaseResponse.fail("码率为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getStatus())){
            wlsmsMongodbConf.setStatus("off");
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

    /**
     * 验证mongodb数据连接
     */

    @RequestMapping("getTestConnect")
    public BaseResponse getTestConnect(WlsmsMongodbConf wlsmsMongodbConf){
        if(null == wlsmsMongodbConf){
            return BaseResponse.fail("参数异常");
        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongodbIp())){
//            return BaseResponse.fail("数据库ip为空");
//        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongodbDatabase())){
//            return BaseResponse.fail("数据库名为空");
//        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getCollectionName())){
            return BaseResponse.fail("collection集合为空");
        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongoUser())){
//            return BaseResponse.fail("用户名为空");
//        }
//        if(StringUtils.isBlank(wlsmsMongodbConf.getMongoPwd())){
//            return BaseResponse.fail("密码为空");
//        }
        return mongoService.getTestConnect(wlsmsMongodbConf);
    }
}
