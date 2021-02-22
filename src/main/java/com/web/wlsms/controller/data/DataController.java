package com.web.wlsms.controller.data;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.DataEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.data.DataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping("insertData")
    public Map<String,Object> insertData(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setDataValueA("1");
            dataEntity.setDataValueB("1");
            dataEntity.setDataValueC("1");
            dataEntity.setDataValueD("1");
            dataEntity.setDataValueE("1");
            dataEntity.setDataValueF("1");
            dataEntity.setDataValueG("1");
            dataEntity.setDataValueH("1");
            dataEntity.setDataValueI("1");
            dataEntity.setDataValueJ("1");
            dataEntity.setDataValueK("1");
            dataEntity.setDataValueL("1");
            dataEntity.setDataValueM("1");
            dataEntity.setDataValueN("1");
            dataEntity.setPositionCode("code1");
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = spf.format(new Date());
            dataEntity.setCreateTime(createTime);
            dataService.insertData(dataEntity);
            resultMap.put("data","数据添加成功");
        }catch (Exception e){
            resultMap.put("data","数据添加失败");
        }
        return resultMap;
    }
}
