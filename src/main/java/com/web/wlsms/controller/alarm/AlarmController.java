package com.web.wlsms.controller.alarm;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.alarm.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Api(tags = {"告警管理"})
@RestController
@RequestMapping("/alarm/alarm")
public class AlarmController {
    @Resource
    private AlarmService alarmService;

    @ApiOperation("告警预值配置")
    @PostMapping("getAlarmConfig")
    public Map<String,Object> getAlarmConfig(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getAlarmConfig = alarmService.getAlarmConfig(params);
            resultMap.put("total", getAlarmConfig.getTotal());
            resultMap.put("rows", getAlarmConfig.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @ApiOperation("告警采集列表")
    @PostMapping("getAlarmInfoList")
    public Map<String,Object> getAlarmInfoList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getAlarmInfoList = alarmService.getAlarmInfoList(params);
            resultMap.put("total", getAlarmInfoList.getTotal());
            resultMap.put("rows", getAlarmInfoList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
}
