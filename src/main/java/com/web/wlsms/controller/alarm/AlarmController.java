package com.web.wlsms.controller.alarm;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.alarm.AlarmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alarm/alarm")
public class AlarmController {
    @Resource
    private AlarmService alarmService;

    @RequestMapping("getAlarmConfig")
    public Map<String,Object> getDataList(SimpleRequest params){
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

    @RequestMapping("getAlarmInfoList")
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
