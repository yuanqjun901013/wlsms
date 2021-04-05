package com.web.wlsms.controller.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.DataEntity;
import com.web.wlsms.entity.ManualDataModel;
import com.web.wlsms.request.ExcelReadResult;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.data.DataService;
import com.web.wlsms.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/data/data")
public class DataController {
    @Resource
    private DataService dataService;

    /**
     * 数据汇总列表
     * @param params
     * @return
     */
    @RequestMapping("getDataList")
    public Map<String,Object> getDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map map = new HashMap();
            map.put("startTime","2021-02-01 00:00:01");
            map.put("endTime","2021-02-23 12:00:01");
            params.setRequest(map);
            PageInfo getDataList = dataService.getDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 人工上报数据
     * @param params
     * @return
     */
    @RequestMapping("getManualDataList")
    public Map<String,Object> getManualDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map map = new HashMap();
            map.put("startTime","2021-02-01 00:00:01");
            map.put("endTime","2021-02-23 12:00:01");
            params.setRequest(map);
            PageInfo getDataList = dataService.getManualDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 机器上报数据
     * @param params
     * @return
     */
    @RequestMapping("getMachineDataList")
    public Map<String,Object> getMachineDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            Map map = new HashMap();
            map.put("startTime","2021-02-01 00:00:01");
            map.put("endTime","2021-02-23 12:00:01");
            params.setRequest(map);
            PageInfo getDataList = dataService.getMachineDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 数据分析处理列表
     * @param params
     * @return
     */
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

    /**
     * 报表总览
     * @param params
     * @return
     */
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

    /**
     * 详细报表
     * @param params
     * @return
     */
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

    /**
     * 阵地报表
     * @param params
     * @return
     */
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

    /**
     * 添加主表数据
     * @param params
     * @return
     */
    @RequestMapping("insertData")
    public Map<String,Object> insertData(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            DataEntity dataEntity = new DataEntity();
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

    /**
     * 导入人工底数
     */
    @RequestMapping("importManual")
    public BaseResponse importManual(@RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            if (null == file) return BaseResponse.fail("读取Excel异常！");
            InputStream inputStream = file.getInputStream();// 得到输入流
            if (null != inputStream) {
                ExcelReadResult<ManualDataModel> excelRead = ExcelUtil.readList("manualData.xml", inputStream, ManualDataModel.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 数据预处理
                    Map<String, Object> keyMap = new HashMap<>();
                    for (ManualDataModel excelModel : excelRead.getResult()) {
                        String msg = checkExcelData(excelModel);
                        if (msg == null) {
                            String key = excelModel.getSxzfqName().trim() + "#" + excelModel.getSxplValue().trim();
                            if (keyMap.containsKey(key)) {
                                return BaseResponse.fail("上行转发器=" + excelModel.getSxzfqName().trim() + ",上行频率=" + excelModel.getSxplValue().trim() + "的数据行存在重复！");
                            }
                            keyMap.put(key, null);
                            continue;
                        } else {
                            return BaseResponse.fail(msg);
                        }
                    }
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelData(excelRead.getResult());
                    if (result.getCode().equals("0000") && null != result.getData()) {
                        return BaseResponse.ok("成功导入" + result.getData() + "条数据！");
                    } else {
                        return BaseResponse.fail("数据导入异常！");
                    }
                }
            }
            return BaseResponse.fail("excel文件内容为空!");
        } catch (Exception e) {
            return BaseResponse.fail("导入失败！");
        }
    }

        //校验excel数据格式是否规范
        private String checkExcelData(ManualDataModel excelModel) {
            String sxzfqName = excelModel.getSxzfqName().trim();
            if (StringUtils.isBlank(sxzfqName)){
                return "上行转发器列存在空值";
            }
            String sxplValue = excelModel.getSxplValue().trim();
            if (StringUtils.isBlank(sxplValue)) {
                return "上行转发器="+sxzfqName+"的数据行，上行频率列存在空值";
            }
            return null;
        }

    //写入excel数据
    public BaseResponse<Integer> writeExcelData(List<ManualDataModel> list) {
        List<ManualDataModel> strategyList = new ArrayList<>();
        for (ManualDataModel model : list) {
            ManualDataModel strategy = new ManualDataModel();
            String sxzfqName = model.getSxzfqName().trim();
            strategy.setSxzfqName(sxzfqName);
            strategy.setSxplValue(model.getSxplValue().trim());
            strategyList.add(strategy);
        }
        //调用接口写入数据
        return null;
    }
}
