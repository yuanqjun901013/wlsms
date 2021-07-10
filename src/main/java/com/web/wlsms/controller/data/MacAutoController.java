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
            strategy.setWxName(model.getWxName().trim());
            strategy.setZplValue(model.getZplValue().trim());
            strategy.setDplValue(model.getDplValue().trim());
            strategy.setTkplValue(model.getTkplValue().trim());
            strategy.setXhType(model.getXhType().trim());
            strategy.setMslValue(model.getMslValue().trim());
            strategy.setZzbValue(model.getZzbValue().trim());
            strategy.setTzysName(model.getTzysName().trim());
            strategy.setBuildTime(model.getBuildTime());
            strategy.setBmType(model.getBmType());
            strategy.setMlName(model.getMlName());
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
        return macAutoService.saveManual(manualModel);
    }

    @RequestMapping("updateManual")
    public BaseResponse updateManual(ManualModel manualModel){
        if(null == manualModel){
            return BaseResponse.fail("入参有误，请重试");
        }
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


}
