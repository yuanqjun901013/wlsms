package com.web.wlsms.controller.data;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.ExcelReadResult;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.data.MacAutoService;
import com.web.wlsms.service.system.MessageService;
import com.web.wlsms.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/macAuto")
public class MacAutoController {
    @Resource
    private MacAutoService macAutoService;
    @Autowired
    MessageService messageService;

    /**
     * 导入人工底数
     */
    @RequestMapping("importManual")
    @ResponseBody
    public BaseResponse importManual(HttpServletRequest request, MultipartFile file, String positionCode) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        try {
            if (null == file) return BaseResponse.fail("读取Excel异常！");
            InputStream inputStream = file.getInputStream();// 得到输入流
            if(null != inputStream) {
                ExcelReadResult<ManualModel> excelRead = ExcelUtil.readList("manual.xml", inputStream, ManualModel.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelManual(excelRead.getResult(),positionCode,userNo);
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

    public BaseResponse<Integer> writeExcelManual(List<ManualModel> list, String positionCode, String userNo) {
        if(null == list && list.size() ==0 ){
            return BaseResponse.fail("数据为空");
        }
        int num = 0;
        List<ManualModel> strategyList = new ArrayList<>();
        String proCode = getProCodeNum();//获取批次公文号
        for (ManualModel model : list) {
            ManualModel strategy = new ManualModel();
            strategy.setSxzfqName(model.getSxzfqName().replace(" ","").trim());
            strategy.setSxplValue(model.getSxplValue().replace(" ","").trim());
            strategy.setBpqplValue(model.getBpqplValue().replace(" ","").trim());
            strategy.setZplValue(model.getZplValue().replace(" ","").trim());
            strategy.setXxplValue(model.getXxplValue().replace(" ","").trim());
            strategy.setSystemName(model.getSystemName().replace(" ","").trim());
            strategy.setTzslValue(model.getTzslValue().replace(" ","").trim());
            strategy.setXxslValue(model.getXxslValue().replace(" ","").trim());
            strategy.setTzfsName(model.getTzfsName().replace(" ","").trim());
            strategy.setXdbmCode(model.getXdbmCode().replace(" ","").trim());
            strategy.setXzbValue(model.getXzbValue().replace(" ","").trim());
            strategy.setBuildDate(model.getBuildDate());
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            strategyList.add(strategy);
        }
        if (null != strategyList && strategyList.size() >0){
            //排重
            Set<ManualModel> setData = new HashSet<ManualModel>();
            setData.addAll(strategyList);
            List<ManualModel> newAddData = new ArrayList<>();
            newAddData.addAll(setData);
            //分批次 批量保存
            if (newAddData.size() > 200) {
                long total = newAddData.size();
                long remain = total % 200;
                long times = total / 200;
                long realTimes = remain == 0 ? times : times + 1;
                for (long i = 0; i < realTimes; i++) {
                    List<ManualModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                    num += macAutoService.insertManual(batchList);
                }
            } else {
                num += macAutoService.insertManual(newAddData);
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
     * 导入机器底数
     */
    @RequestMapping("importMachine")
    @ResponseBody
    public BaseResponse importMachine(HttpServletRequest request,MultipartFile file, String positionCode) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        try {
            if (null == file) return BaseResponse.fail("读取Excel异常！");
            InputStream inputStream = file.getInputStream();// 得到输入流
            if(null != inputStream) {
                ExcelReadResult<MachineModel> excelRead = ExcelUtil.readList("machine.xml", inputStream, MachineModel.class);
                if (excelRead.isStatus() && !excelRead.getResult().isEmpty()) {
                    // 写入数据
                    BaseResponse<Integer> result = this.writeExcelMachine(excelRead.getResult(),positionCode,userNo);
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

    public BaseResponse<Integer> writeExcelMachine(List<MachineModel> list,String positionCode,String userNo) {
        if(null == list && list.size() ==0 ){
            return BaseResponse.fail("数据为空");
        }
        int num = 0;
        List<MachineModel> strategyList = new ArrayList<>();
        String proCode = getProCodeNum();//获取批次公文号
        for (MachineModel model : list) {
            MachineModel strategy = new MachineModel();
            strategy.setWxName(model.getWxName().replace(" ","").trim());
            strategy.setZplValue(model.getZplValue().replace(" ","").trim());
            strategy.setDplValue(model.getDplValue().replace(" ","").trim());
            strategy.setTkplValue(model.getTkplValue().replace(" ","").trim());
            strategy.setXhType(model.getXhType().replace(" ","").trim());
            strategy.setMslValue(model.getMslValue().replace(" ","").trim());
            strategy.setZzbValue(model.getZzbValue().replace(" ","").trim());
            strategy.setTzysName(model.getTzysName().replace(" ","").trim());
            strategy.setBuildTime(model.getBuildTime());
            strategy.setBmType(model.getBmType().replace(" ","").trim());
            strategy.setMlName(model.getMlName().replace(" ","").trim());
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            strategyList.add(strategy);
        }
        if (null != strategyList && strategyList.size() >0){
            //排重
            Set<MachineModel> setData = new HashSet<MachineModel>();
            setData.addAll(strategyList);
            List<MachineModel> newAddData= new ArrayList<>();
            newAddData.addAll(setData);
            //分批次 批量保存
            if (newAddData.size() > 200) {
                long total = newAddData.size();
                long remain = total % 200;
                long times = total / 200;
                long realTimes = remain == 0 ? times : times + 1;
                for (long i = 0; i < realTimes; i++) {
                    List<MachineModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                    num += macAutoService.insertMachine(batchList);
                }
            } else {
                num += macAutoService.insertMachine(newAddData);
            }
            //自动比对人工数据(获取最新日期的人工数据)
            String buildDate = macAutoService.queryLimitDate();
            List<ManualModel> getManualList = macAutoService.getManualListByDate(buildDate);
            //处理对比
            try {
                macAutoService.getAutoDocker(newAddData, getManualList, "系统");
            }catch (Exception e){

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
     * 人工上报数据
     * @param params
     * @return
     */
    @RequestMapping("getManualList")
    public Map<String,Object> getManualDataList(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = macAutoService.getManualList(params);
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
    @RequestMapping("getMachineList")
    public Map<String,Object> getMachineList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = macAutoService.getMachineList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("saveManual")
    public BaseResponse saveManual(HttpServletRequest request, ManualModel manualModel){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == manualModel){
            return BaseResponse.fail("入参有误");
        }
        if(StringUtils.isBlank(userNo)){
            return BaseResponse.fail("用户没登录");
        }
        manualModel.setUserNo(userNo);
        manualModel.setProCode(getProCodeNum());
        manualModel.setXdbmCode(manualModel.getXdbmCode().replace(" ","").trim());
        manualModel.setSxzfqName(manualModel.getSxzfqName().replace(" ","").trim());
        manualModel.setSxplValue(manualModel.getSxplValue().replace(" ","").trim());
        manualModel.setBpqplValue(manualModel.getBpqplValue().replace(" ","").trim());
        manualModel.setZplValue(manualModel.getZplValue().replace(" ","").trim());
        manualModel.setXxplValue(manualModel.getXxplValue().replace(" ","").trim());
        manualModel.setSystemName(manualModel.getSystemName().replace(" ","").trim());
        manualModel.setTzslValue(manualModel.getTzslValue().replace(" ","").trim());
        manualModel.setXxslValue(manualModel.getXxslValue().replace(" ","").trim());
        manualModel.setTzfsName(manualModel.getTzfsName().replace(" ","").trim());
        manualModel.setXzbValue(manualModel.getXzbValue().replace(" ","").trim());
        return macAutoService.saveManual(manualModel);
    }

    @RequestMapping("updateManual")
    public BaseResponse updateManual(ManualModel manualModel){
        if(null == manualModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        manualModel.setXdbmCode(manualModel.getXdbmCode().replace(" ","").trim());
        manualModel.setSxzfqName(manualModel.getSxzfqName().replace(" ","").trim());
        manualModel.setSxplValue(manualModel.getSxplValue().replace(" ","").trim());
        manualModel.setBpqplValue(manualModel.getBpqplValue().replace(" ","").trim());
        manualModel.setZplValue(manualModel.getZplValue().replace(" ","").trim());
        manualModel.setXxplValue(manualModel.getXxplValue().replace(" ","").trim());
        manualModel.setSystemName(manualModel.getSystemName().replace(" ","").trim());
        manualModel.setTzslValue(manualModel.getTzslValue().replace(" ","").trim());
        manualModel.setXxslValue(manualModel.getXxslValue().replace(" ","").trim());
        manualModel.setTzfsName(manualModel.getTzfsName().replace(" ","").trim());
        manualModel.setXzbValue(manualModel.getXzbValue().replace(" ","").trim());
        return macAutoService.updateManual(manualModel);
    }

    /**
     * 删除人工底数
     * @param
     * @return
     */
    @RequestMapping("deleteManual")
    public BaseResponse deleteManual(ManualModel manualModel){
        if(null == manualModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        return macAutoService.deleteManual(manualModel);
    }

    /**
     * 删除机器底数
     * @param
     * @return
     */
    @RequestMapping("deleteMachine")
    public BaseResponse deleteMachine(MachineModel machineModel){
        if(null == machineModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        return macAutoService.deleteMachine(machineModel);
    }


    /**
     * 人工上报数据以日期为分类
     * @param params
     * @return
     */
    @RequestMapping("queryManualByDate")
    public Map<String,Object> queryManualByDate(Map params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<AutoBuildEntity> getDataList = macAutoService.queryManualByDate(params);
            resultMap.put("rows", getDataList);

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 机器上报数据以时间点为分类
     * @param params
     * @return
     */
    @RequestMapping("queryMachineByDate")
    public Map<String,Object> queryMachineByDate(Map params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<AutoBuildEntity> getDataList = macAutoService.queryMachineByDate(params);
            resultMap.put("rows", getDataList);
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 查询比对标记表数据
     * @param params
     * @return
     */
    @RequestMapping("queryAutoBuildList")
    public Map<String,Object> queryAutoBuildList(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = macAutoService.queryAutoBuildList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }



    /**
     * 查询比对详情表数据
     * @param params
     * @return
     */
    @RequestMapping("getAutoDataList")
    public Map<String,Object> getAutoDataList(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDataList = macAutoService.getAutoDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 根据主表id查询比对详情表数据
     * @param params
     * @return
     */
    @RequestMapping("getAutoDataListById")
    public Map<String,Object> getAutoDataListById(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            AutoBuildEntity autoBuildById = macAutoService.getAutoBuildById(params);
            if(null != autoBuildById){
                params.setBuildDate(autoBuildById.getBuildDate());
                params.setBuildTime(autoBuildById.getBuildTime());
            }
            PageInfo getDataList = macAutoService.getAutoDataList(params);
            resultMap.put("total", getDataList.getTotal());
            resultMap.put("rows", getDataList.getList());

        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @RequestMapping("openAuto")
    public BaseResponse openAuto(SimpleRequest<Map> params){
        if(null == params){
            return BaseResponse.fail("入参有误，请重试");
        }
        return macAutoService.openAuto(params);
    }
}
