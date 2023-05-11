package com.web.wlsms.controller.mongodb;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.mongodb.MongoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"mongo数据库配置"})
@RestController
@RequestMapping("/mongo/config")
public class MongoConfigController {
    @Resource
    private MongoService mongoService;

    @ApiOperation("mongoDb保存配置")
    @PostMapping("saveMongoConfig")
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
        if(StringUtils.isBlank(wlsmsMongodbConf.getCarPol())){
            return BaseResponse.fail("极化方式为空");
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
        if(StringUtils.isBlank(wlsmsMongodbConf.getAppearTime())){
            return BaseResponse.fail("发现时间为空");
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
        if(StringUtils.isBlank(wlsmsMongodbConf.getMuladdr())){
            return BaseResponse.fail("多址方式为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getOthers())){
            return BaseResponse.fail("其他为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getExmlen())){
            return BaseResponse.fail("分组长度为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getFcycle())){
            return BaseResponse.fail("突发周期为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getFlen())){
            return BaseResponse.fail("帧长为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getCf())){
            return BaseResponse.fail("差分为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getRm())){
            return BaseResponse.fail("扰码为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getSindex())){
            return BaseResponse.fail("索引号为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getUserProperties())){
            return BaseResponse.fail("用户属性为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getAppearTime())){
            return BaseResponse.fail("发现时间为空");
        }
        if(StringUtils.isBlank(wlsmsMongodbConf.getStatus())){
            wlsmsMongodbConf.setStatus("off");
        }
        return mongoService.saveMongoConfig(wlsmsMongodbConf);
    }

    @ApiOperation("mongoDb获取配置")
    @PostMapping("getMongoDbList")
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
    @ApiOperation("验证mongodb数据连接")
    @PostMapping("getTestConnect")
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
