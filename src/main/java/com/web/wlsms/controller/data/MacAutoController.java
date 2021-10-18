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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        //获取当前日期
        SimpleDateFormat bartDateFormat =
                new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String buildDate = bartDateFormat.format(date);
        for (ManualModel model : list) {
            ManualModel strategy = new ManualModel();
            strategy.setSxzfqName(StringUtils.isBlank(model.getSxzfqName()) == true ? "" : model.getSxzfqName().replace(" ","").trim());
//            strategy.setSxplValue(model.getSxplValue().replace(" ","").trim());
            strategy.setCarPol(StringUtils.isBlank(model.getCarPol()) == true ? "" : model.getCarPol().replace(" ","").trim());
            strategy.setBpqplValue(StringUtils.isBlank(model.getBpqplValue()) == true ? "0" : model.getBpqplValue().replace(" ","").trim());
            strategy.setZplValue(StringUtils.isBlank(model.getZplValue()) == true ? "0" : model.getZplValue().replace(" ","").trim());
            strategy.setXxplValue(StringUtils.isBlank(model.getXxplValue()) == true ? "0" : model.getXxplValue().replace(" ","").trim());
            strategy.setSystemName(StringUtils.isBlank(model.getSystemName()) == true ? "" : model.getSystemName().replace(" ","").trim());
            strategy.setTzslValue(StringUtils.isBlank(model.getTzslValue()) == true ? "0" : model.getTzslValue().replace(" ","").trim());
            strategy.setXxslValue(StringUtils.isBlank(model.getXxslValue()) == true ? "0" : model.getXxslValue().replace(" ","").trim());
            strategy.setTzfsName(StringUtils.isBlank(model.getTzfsName()) == true ? "" : model.getTzfsName().replace(" ","").trim());
            strategy.setXdbmCode(StringUtils.isBlank(model.getXdbmCode()) == true ? "" : model.getXdbmCode().replace(" ","").trim());
            strategy.setMlName(StringUtils.isBlank(model.getMlName()) == true ? "" : model.getMlName().replace(" ","").replace("'","").trim());
            strategy.setXzbValue(StringUtils.isBlank(model.getXzbValue()) == true ? "0" : model.getXzbValue().replace(" ","").trim());
            strategy.setFlen(StringUtils.isBlank(model.getFlen()) == true ? "" : model.getFlen().replace(" ","").trim());
            strategy.setRemark(StringUtils.isBlank(model.getRemark()) == true ? "" : model.getRemark().replace(" ","").trim());
            strategy.setBuildDate(buildDate);
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            //简化码率分数(mlName)
            strategy.setMlName(getMlName(strategy.getMlName()));
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

    //简化分数
    private String getMlName(String mlNameOld){
        if(StringUtils.isBlank(mlNameOld)){
            return "";
        }
        String[] mlArr= mlNameOld.split("/");
        boolean isNumA = mlArr[1].matches("[0-9]+");
        boolean isNumB = mlArr[0].matches("[0-9]+");
        if(isNumA && isNumB) {
            int a = Integer.parseInt(mlArr[1]), b = Integer.parseInt(mlArr[0]);//a 是分母
            int gcd = gcd(a, b);
//            System.out.println(b / gcd + "/" + a / gcd); // 输出了 5/6
            return b / gcd + "/" + a / gcd;
        }else {
            return mlNameOld;
        }
    }

    private static int gcd(int x, int y){ // 这个是运用辗转相除法求 两个数的 最大公约数 看不懂可以百度 // 下
        if(y == 0)
            return x;
        else
            return gcd(y,x%y);
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
        //获取当前日期
        SimpleDateFormat bartDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String buildTime = bartDateFormat.format(date);
        for (MachineModel model : list) {
            MachineModel strategy = new MachineModel();
            strategy.setWxName(StringUtils.isBlank(model.getWxName()) == true ? "" : model.getWxName().replace(" ","").trim());
            strategy.setCarPol(StringUtils.isBlank(model.getCarPol()) == true ? "" : model.getCarPol().replace(" ","").trim());
            strategy.setDplValue(StringUtils.isBlank(model.getDplValue()) == true ? "0" : model.getDplValue().replace(" ","").trim());
            strategy.setTkplValue(StringUtils.isBlank(model.getTkplValue()) == true ? "0" : model.getTkplValue().replace(" ","").trim());
            strategy.setXhType(StringUtils.isBlank(model.getXhType()) == true ? "" : model.getXhType().replace(" ","").trim());
            strategy.setMslValue(StringUtils.isBlank(model.getMslValue()) == true ? "0" : model.getMslValue().replace(" ","").trim());
            strategy.setZzbValue(StringUtils.isBlank(model.getZzbValue()) == true ? "0" : model.getZzbValue().replace(" ","").trim());
            strategy.setTzysName(StringUtils.isBlank(model.getTzysName()) == true ? "" : model.getTzysName().replace(" ","").trim());
            strategy.setBmType(StringUtils.isBlank(model.getBmType()) == true ? "" : model.getBmType().replace(" ","").trim());
            strategy.setMlName(StringUtils.isBlank(model.getMlName()) == true ? "" : model.getMlName().replace(" ","").replace("'","").trim());
            strategy.setMuladdr(StringUtils.isBlank(model.getMuladdr()) == true ? "" : model.getMuladdr().replace(" ","").trim());
            strategy.setOthers(StringUtils.isBlank(model.getOthers()) == true ? "" : model.getOthers().replace(" ","").trim());
            strategy.setExmlen(StringUtils.isBlank(model.getExmlen()) == true ? "" : model.getExmlen().replace(" ","").trim());
            strategy.setFcycle(StringUtils.isBlank(model.getFcycle()) == true ? "" : model.getFcycle().replace(" ","").trim());
            strategy.setFlen(StringUtils.isBlank(model.getFlen()) == true ? "" : model.getFlen().replace(" ","").trim());
            strategy.setCf(StringUtils.isBlank(model.getCf()) == true ? "" : model.getCf().replace(" ","").trim());
            strategy.setRm(StringUtils.isBlank(model.getRm()) == true ? "" : model.getRm().replace(" ","").trim());
            strategy.setSindex(StringUtils.isBlank(model.getSindex()) == true ? "" : model.getSindex().replace(" ","").trim());
            strategy.setUserProperties(StringUtils.isBlank(model.getUserProperties()) == true ? "" : model.getUserProperties().replace(" ","").trim());
            strategy.setAppearTime(buildTime);
            strategy.setBuildTime(buildTime);
            strategy.setBuildType("手动导入");
            strategy.setPositionCode(positionCode);
            strategy.setProCode(proCode);
            strategy.setUserNo(userNo);
            //简化码率分数(mlName)
            strategy.setMlName(getMlName(strategy.getMlName()));
            strategyList.add(strategy);
        }
        List<MachineModel> newMachineList = new ArrayList<>();
        //筛选数据是否在数据库中存在
        if(null != strategyList && strategyList.size() > 0){
            for(MachineModel machineModel:strategyList){
                int count = macAutoService.queryMachineCountByInfo(machineModel);
                if(count == 0){
                    newMachineList.add(machineModel);
                }
            }
        }
        if (null != newMachineList && newMachineList.size() >0){
            //排重
            Set<MachineModel> setData = new HashSet<MachineModel>();
            setData.addAll(newMachineList);
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
     * Excel导出
     * @return
     */
//    @RequestMapping("exportConfig")
//    public void exportConfig(@RequestParam String productCode, HttpServletRequest request, HttpServletResponse response) {
//        Map<String,Object> queryParams = new HashMap<>();
//        if (StringUtils.isNotEmpty(productCode)) {
//            queryParams.put("productCode",productCode);
//        }
//        String destFileName = "服务计价策略数据" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xlsx";
//        ExcelUtil.export(request,response,"wlsms_auto_manual.xlsx", destFileName, this.priceStrategy.queryStrategyAll(queryParams));
//    }

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
//        manualModel.setSxplValue(manualModel.getSxplValue().replace(" ","").trim());
        manualModel.setBpqplValue(manualModel.getBpqplValue().replace(" ","").trim());
        manualModel.setZplValue(manualModel.getZplValue().replace(" ","").trim());
        manualModel.setXxplValue(manualModel.getXxplValue().replace(" ","").trim());
        manualModel.setSystemName(manualModel.getSystemName().replace(" ","").trim());
        manualModel.setTzslValue(manualModel.getTzslValue().replace(" ","").trim());
        manualModel.setXxslValue(manualModel.getXxslValue().replace(" ","").trim());
        manualModel.setTzfsName(manualModel.getTzfsName().replace(" ","").trim());
        manualModel.setXzbValue(manualModel.getXzbValue().replace(" ","").trim());
        manualModel.setMlName(manualModel.getMlName().replace(" ","").replace("'","").trim());
        manualModel.setCarPol(manualModel.getCarPol().replace(" ","").trim());
        manualModel.setFlen(manualModel.getFlen().replace(" ","").trim());
        manualModel.setRemark(manualModel.getRemark().replace(" ","").trim());
        return macAutoService.saveManual(manualModel);
    }

    @RequestMapping("updateManual")
    public BaseResponse updateManual(ManualModel manualModel){
        if(null == manualModel){
            return BaseResponse.fail("入参有误，请重试");
        }
        manualModel.setXdbmCode(manualModel.getXdbmCode().replace(" ","").trim());
        manualModel.setMlName(manualModel.getMlName().replace(" ","").replace("'","").trim());
        manualModel.setSxzfqName(manualModel.getSxzfqName().replace(" ","").trim());
        manualModel.setCarPol(manualModel.getCarPol().replace(" ","").trim());
//        manualModel.setSxplValue(manualModel.getSxplValue().replace(" ","").trim());
        manualModel.setBpqplValue(manualModel.getBpqplValue().replace(" ","").trim());
        manualModel.setZplValue(manualModel.getZplValue().replace(" ","").trim());
        manualModel.setXxplValue(manualModel.getXxplValue().replace(" ","").trim());
        manualModel.setSystemName(manualModel.getSystemName().replace(" ","").trim());
        manualModel.setTzslValue(manualModel.getTzslValue().replace(" ","").trim());
        manualModel.setXxslValue(manualModel.getXxslValue().replace(" ","").trim());
        manualModel.setTzfsName(manualModel.getTzfsName().replace(" ","").trim());
        manualModel.setXzbValue(manualModel.getXzbValue().replace(" ","").trim());
        manualModel.setFlen(manualModel.getFlen().replace(" ","").trim());
        manualModel.setRemark(manualModel.getRemark().replace(" ","").trim());
        return macAutoService.updateManual(manualModel);
    }

    /**
     * 删除人工底数
     * @param
     * @return
     */
    @RequestMapping("deleteManual")
    public BaseResponse deleteManual(String ids){
        if(null == ids){
            return BaseResponse.fail("入参有误，请重试");
        }
        List<String> idsArr = Arrays.asList(ids.split(","));
        return macAutoService.deleteManual(idsArr);
    }

    /**
     * 删除机器底数
     * @param
     * @return
     */
    @RequestMapping("deleteMachine")
    public BaseResponse deleteMachine(String ids){
        if(null == ids){
            return BaseResponse.fail("入参有误，请重试");
        }
        List<String> idsArr = Arrays.asList(ids.split(","));
        return macAutoService.deleteMachine(idsArr);
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

    /**
     * 删除汇总融合数据
     * @param
     * @return
     */
    @RequestMapping("deleteAutoBuild")
    public BaseResponse deleteAutoBuild(String ids){
        if(null == ids){
            return BaseResponse.fail("入参有误，请重试");
        }
        List<String> idsArr = Arrays.asList(ids.split(","));
        return macAutoService.deleteAutoBuild(idsArr);
    }

    @RequestMapping("openAuto")
    public BaseResponse openAuto(SimpleRequest<Map> params){
        if(null == params){
            return BaseResponse.fail("入参有误，请重试");
        }
        return macAutoService.openAuto(params);
    }
}
