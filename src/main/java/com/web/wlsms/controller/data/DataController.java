package com.web.wlsms.controller.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.DataProCodeRequest;
import com.web.wlsms.request.ExcelReadResult;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.request.UpLoadRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.data.DataService;
import com.web.wlsms.service.system.MessageService;
import com.web.wlsms.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.MapUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Api(tags = {"数据统计管理"})
@RestController
@RequestMapping("/data/data")
public class DataController {
    @Resource
    private DataService dataService;

    @Autowired
    MessageService messageService;

    /**
     * 数据汇总列表
     * @param params
     * @return
     */
    @ApiOperation("数据汇总列表")
    @PostMapping("getDataList")
    public Map<String,Object> getDataList(SimpleRequest<Map> params){
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
     * 人工上报数据
     * @param params
     * @return
     */
    @ApiOperation("人工上报数据")
    @PostMapping("getManualDataList")
    public Map<String,Object> getManualDataList(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
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
    @ApiOperation("机器上报数据")
    @PostMapping("getMachineDataList")
    public Map<String,Object> getMachineDataList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
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
     * 人工未核对数据
     * @return
     */
    @ApiOperation("人工未核对数据")
    @PostMapping("getManualDit")
    public Map<String,Object> getManualDit(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<ManualDataModel> getDataList = dataService.getManualDit();
            resultMap.put("rows", getDataList);
        }catch (Exception e){
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 机器未核对数据
     * @return
     */
    @ApiOperation("机器未核对数据")
    @PostMapping("getMachineDit")
    public Map<String,Object> getMachineDit(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<MachineDataModel> getDataList = dataService.getMachineDit();
            resultMap.put("rows", getDataList);

        }catch (Exception e){
            resultMap.put("rows", "");
        }
        return resultMap;
    }




    /**
     * 数据分析处理列表
     * @param params
     * @return
     */
    @ApiOperation("数据分析处理列表")
    @PostMapping("queryDataList")
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
    @ApiOperation("报表总览")
    @PostMapping("getDataBi")
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
    @ApiOperation("详细报表")
    @PostMapping("getDataDetail")
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
     * 报表
     * @param params
     * @return
     */
    @ApiOperation("报表")
    @PostMapping("getDataByPosition")
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
    @ApiOperation("添加主表数据")
    @PostMapping("insertData")
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
    @ApiOperation("导入人工底数")
    @PostMapping("importManual")
    @ResponseBody
    public BaseResponse importManual(HttpServletRequest request,MultipartFile file, String positionCode) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        try {
            if (null == file){ return BaseResponse.fail("读取Excel异常！");}
            InputStream inputStream = file.getInputStream();// 得到输入流
            if(null != inputStream) {
                ExcelReadResult<ManualDataModel> excelRead = ExcelUtil.readList("manualData.xml", inputStream, ManualDataModel.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelData(excelRead.getResult(),positionCode,userNo);
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

    public BaseResponse<Integer> writeExcelData(List<ManualDataModel> list,String positionCode,String userNo) {
        if(null == list && list.size() ==0 ){
            return BaseResponse.fail("数据为空");
        }
        int num = 0;
        List<ManualDataModel> strategyList = new ArrayList<>();
        String proCode = getProCodeNum();//获取批次公文号
        for (ManualDataModel model : list) {
            ManualDataModel strategy = new ManualDataModel();
            strategy.setSxzfqName(model.getSxzfqName().trim());
            strategy.setSxplValue(model.getSxplValue().trim());
            strategy.setBpqplValue(model.getBpqplValue().trim());
            strategy.setZplValue(model.getZplValue().trim());
            strategy.setXxplValue(model.getXxplValue().trim());
            strategy.setSystemName(model.getSystemName().trim());
            strategy.setTzslValue(model.getTzslValue().trim());
            strategy.setXxslValue(model.getXxslValue().trim());
            strategy.setTzfsName(model.getTzfsName().trim());
            strategy.setXdbmCode(model.getXdbmCode().trim());
            strategy.setXzbValue(model.getXzbValue().trim());
            strategy.setCjTime(model.getCjTime());
            strategy.setWzlValue(model.getWzlValue());
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            strategyList.add(strategy);
            }
        if (null != strategyList && strategyList.size() >0){
            //排重
            Set<ManualDataModel> setData = new HashSet<ManualDataModel>();
            setData.addAll(strategyList);
            List<ManualDataModel> newAddData = new ArrayList<>();
            newAddData.addAll(setData);
            //分批次 批量保存
            if (newAddData.size() > 200) {
                long total = newAddData.size();
                long remain = total % 200;
                long times = total / 200;
                long realTimes = remain == 0 ? times : times + 1;
                for (long i = 0; i < realTimes; i++) {
                    List<ManualDataModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                    num += dataService.insertManualData(batchList);
                }
            } else {
                num += dataService.insertManualData(newAddData);
            }
        }
        if(num > 0){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setUserNo(userNo);
            messageEntity.setTitle("上报人工数据");
            messageEntity.setContent(userNo+":上报人工数据,公文号为"+proCode);
            messageEntity.setOperationType(1);
            messageService.insertMessage(messageEntity);
            return BaseResponse.ok(num);
        }else {
            return BaseResponse.fail("入库失败");
        }
    }



    /**
     * 生成公文号：5位项目名称 + 17为时间戳 + 6位随机数字
     *
     * @return 随机唯一字符串
     */
    private String getProCodeNum() {

        StringBuilder stringBuilder = new StringBuilder();
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        //获取十位随机数
        String randomStr = getNumberRandom(6);

        return stringBuilder.append("WLSMS").append(dateStr).append("-").append(randomStr).toString();
    }
    /**
     * 生成随机数字
     *
     * @param length 生成随机数的长度
     * @return 随机唯一字符串
     */
    private String getNumberRandom(int length) {
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < length; i++) {
            SecureRandom random = new SecureRandom();
            // 输出字母还是数字
            if (random.nextBoolean()) {
                // 输出大写或小写字母
                char temp = random.nextBoolean() ? 'a' : 'A';
                val.append((char) (random.nextInt(26) + temp));
            } else {
                // 输出数字
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 删除人工底数
     * @param
     * @return
     */
    @ApiOperation("删除人工底数")
    @PostMapping("deleteManual")
    public BaseResponse deleteManual(ManualDataModel manualDataModel){
        if(null == manualDataModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        return dataService.deleteManual(manualDataModel);
    }

    /**
     * 删除机器底数
     * @param
     * @return
     */
    @ApiOperation("删除机器底数")
    @PostMapping("deleteMachine")
    public BaseResponse deleteMachine(MachineDataModel machineDataModel){
        if(null == machineDataModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        return dataService.deleteMachine(machineDataModel);
    }

    /**
     * 删除汇总归档底数
     * @param
     * @return
     */
    @ApiOperation("删除汇总归档底数")
    @PostMapping("deleteData")
    public BaseResponse deleteData(DataEntity dataEntity){
        if(null == dataEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return dataService.deleteData(dataEntity);
    }

    /**
     * 修改汇总归档底数
     * @param
     * @return
     */
    @ApiOperation("修改汇总归档底数")
    @PostMapping("updateData")
    public BaseResponse updateData(DataEntity dataEntity){
        if(null == dataEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return dataService.updateData(dataEntity);
    }


    /**
     * 导入机器底数
     */
    @ApiOperation("导入机器底数")
    @PostMapping("importMachine")
    @ResponseBody
    public BaseResponse importMachine(HttpServletRequest request,MultipartFile file, String positionCode) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        try {
            if (null == file){ return BaseResponse.fail("读取Excel异常！");}
            InputStream inputStream = file.getInputStream();// 得到输入流
            if(null != inputStream) {
                ExcelReadResult<MachineDataModel> excelRead = ExcelUtil.readList("machineData.xml", inputStream, MachineDataModel.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelDataMachine(excelRead.getResult(),positionCode,userNo);
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

    public BaseResponse<Integer> writeExcelDataMachine(List<MachineDataModel> list,String positionCode,String userNo) {
        if(null == list && list.size() ==0 ){
            return BaseResponse.fail("数据为空");
        }
        int num = 0;
        List<MachineDataModel> strategyList = new ArrayList<>();
        String proCode = getProCodeNum();//获取批次公文号
        for (MachineDataModel model : list) {
            MachineDataModel strategy = new MachineDataModel();
            strategy.setWxName(model.getWxName().trim());
            strategy.setZplValue(model.getZplValue().trim());
            strategy.setDplValue(model.getDplValue().trim());
            strategy.setTkplValue(model.getTkplValue().trim());
            strategy.setXhType(model.getXhType().trim());
            strategy.setMslValue(model.getMslValue().trim());
            strategy.setZzbValue(model.getZzbValue().trim());
            strategy.setTzysName(model.getTzysName().trim());
            strategy.setCjTime(model.getCjTime());
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            strategyList.add(strategy);
        }
        if (null != strategyList && strategyList.size() >0){
            //排重
            Set<MachineDataModel> setData = new HashSet<MachineDataModel>();
            setData.addAll(strategyList);
            List<MachineDataModel> newAddData= new ArrayList<>();
            newAddData.addAll(setData);
            //分批次 批量保存
            if (newAddData.size() > 200) {
                long total = newAddData.size();
                long remain = total % 200;
                long times = total / 200;
                long realTimes = remain == 0 ? times : times + 1;
                for (long i = 0; i < realTimes; i++) {
                    List<MachineDataModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                    num += dataService.insertMachineData(batchList);
                }
            } else {
                num += dataService.insertMachineData(newAddData);
            }
        }
        if(num > 0){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setUserNo(userNo);
            messageEntity.setTitle("上报机器数据");
            messageEntity.setContent(userNo+":上报机器数据,公文号为"+proCode);
            messageEntity.setOperationType(1);
            messageService.insertMessage(messageEntity);
            return BaseResponse.ok(num);
        }else {
            return BaseResponse.fail("入库失败");
        }
    }

    /**
     * 校对机器、人工数据汇总
     * @param request
     * @return
     */
    @ApiOperation("校对机器、人工数据汇总")
    @PostMapping("saveBatch")
    public BaseResponse saveBatch(DataProCodeRequest request){
        try {
            return dataService.saveBatch(request);
        } catch (Exception e) {
            return BaseResponse.fail("操作失败！");
        }
    }


    /**
     * 系统总览
     * @param request
     * @return
     */
    @ApiOperation("系统总览")
    @PostMapping("cotData")
    public BaseResponse cotData(HttpServletRequest request){
        try {
            List<AllParamEntity> allParamList = dataService.cotData();
            if (null != allParamList && allParamList.size() > 0) {
                return BaseResponse.ok(allParamList);
            }
            return BaseResponse.fail("统计汇总失败！");
        } catch (Exception e) {
            return BaseResponse.fail("查询异常！");
        }
    }

    /**
     * 底数录入情况统计
     * @param request
     * @return
     */
    @ApiOperation("底数录入情况统计")
    @PostMapping("vcData")
    public BaseResponse vcData(HttpServletRequest request){
        try {
            List<AllParamEntity> allParamList = dataService.vcData();
            if (null != allParamList && allParamList.size() > 0) {
                return BaseResponse.ok(allParamList);
            }
            return BaseResponse.fail("统计汇总失败！");
        } catch (Exception e) {
            return BaseResponse.fail("查询异常！");
        }
    }

    @ApiOperation("采集时间参数")
    @PostMapping("getRecommend")
    public BaseResponse getRecommend(HttpServletRequest request){
        String cjTime = request.getParameter("cjTime");
        if(StringUtils.isBlank(cjTime)){
            return BaseResponse.fail("采集时间参数为空，请重试");
        }
        return dataService.getRecommend(cjTime);
    }


    /**
     * 导入人工底数
     */
    @ApiOperation("导入人工底数")
    @PostMapping("importBatchData")
    @ResponseBody
    public BaseResponse importBatchData(HttpServletRequest request,MultipartFile file, String positionCode) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        try {
            if (null == file) {return BaseResponse.fail("读取Excel异常！");}
            InputStream inputStream = file.getInputStream();// 得到输入流
            if(null != inputStream) {
                ExcelReadResult<DataEntity> excelRead = ExcelUtil.readList("batchData.xml", inputStream, DataEntity.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelBatchData(excelRead.getResult(),positionCode,userNo);
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

    public BaseResponse<Integer> writeExcelBatchData(List<DataEntity> list,String positionCode,String userNo) {
        if(null == list && list.size() ==0 ){
            return BaseResponse.fail("数据为空");
        }
        int num = 0;
        List<DataEntity> strategyList = new ArrayList<>();
        String proCode = getProCodeNum();//获取批次公文号
        for (DataEntity model : list) {
            DataEntity strategy = new DataEntity();
            strategy.setSxzfqName(model.getSxzfqName().trim());
            strategy.setSxplValue(model.getSxplValue().trim());
            strategy.setBpqplValue(model.getBpqplValue().trim());
            strategy.setZplValue(model.getZplValue().trim());
            strategy.setXxplValue(model.getXxplValue().trim());
            strategy.setSystemName(model.getSystemName().trim());
            strategy.setTzslValue(model.getTzslValue().trim());
            strategy.setXxslValue(model.getXxslValue().trim());
            strategy.setTzfsName(model.getTzfsName().trim());
            strategy.setXdbmCode(model.getXdbmCode().trim());
            strategy.setContent(model.getContent().trim());
            strategy.setXzbValue(model.getXzbValue().trim());
            strategy.setErrorContent(model.getErrorContent());
            strategy.setRemark(model.getRemark());
            strategy.setCjTime(model.getCjTime());
            strategy.setWzlValue(model.getWzlValue());
            strategy.setOtherUserCode(model.getOtherUserCode());
            strategy.setOtherKxCode(model.getOtherKxCode());
            strategy.setOtherUserTitle(model.getOtherUserTitle());
            strategy.setOtherKxTitle(model.getOtherKxTitle());
            strategy.setQrxdContent(model.getQrxdContent());
            strategy.setZhsjContent(model.getZhsjContent());
            strategy.setPassportRemark(model.getPassportRemark());
            strategy.setMobileUnitValue(model.getMobileUnitValue());
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            strategyList.add(strategy);
        }
        if (null != strategyList && strategyList.size() >0){
            //排重
            Set<DataEntity> setData = new HashSet<>();
            setData.addAll(strategyList);
            List<DataEntity> newAddData = new ArrayList<>();
            newAddData.addAll(setData);
            //分批次 批量保存
            if (newAddData.size() > 200) {
                long total = newAddData.size();
                long remain = total % 200;
                long times = total / 200;
                long realTimes = remain == 0 ? times : times + 1;
                for (long i = 0; i < realTimes; i++) {
                    List<DataEntity> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                    num += dataService.insertBatchData(batchList);
                }
            } else {
                num += dataService.insertBatchData(newAddData);
            }
        }
        if(num > 0){
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setUserNo(userNo);
            messageEntity.setTitle("批量导入人工数据");
            messageEntity.setContent(userNo+":批量导入人工数据,公文号为"+proCode);
            messageEntity.setOperationType(1);
            messageService.insertMessage(messageEntity);
            return BaseResponse.ok(num);
        }else {
            return BaseResponse.fail("入库失败");
        }
    }

    /**
     * 最近七天底数情况折线对比图
     * @param request
     * @return
     */
    @ApiOperation("最近七天底数情况折线对比图")
    @PostMapping("chartData")
    public BaseResponse chartData(HttpServletRequest request){
        try {
            Map<String, Object> allParam = dataService.chartData();
            if (null != allParam && allParam.size() > 0) {
                return BaseResponse.ok(allParam);
            }
            return BaseResponse.fail("统计汇总失败！");
        } catch (Exception e) {
            return BaseResponse.fail("查询异常！");
        }
    }

}
