package com.web.wlsms.controller.data;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.data.DataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/data/data")
public class DataController {
    @Resource
    private DataService dataService;

    @RequestMapping("getDataList")
    public Map<String,Object> getDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("queryDataList")
    public Map<String,Object> queryDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("getDataBi")
    public Map<String,Object> getDataBi(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("getDataDetail")
    public Map<String,Object> getDataDetail(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("getDataByPosition")
    public Map<String,Object> getDataByPosition(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
}
