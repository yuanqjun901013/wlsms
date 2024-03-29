package com.web.wlsms.service.data;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MacAutoDao;
import com.web.wlsms.dao.PositionDao;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.alarm.AlarmService;
import com.web.wlsms.service.system.MessageService;
import com.web.wlsms.service.system.PositionService;
import com.web.wlsms.utils.CollectionUtils;
import org.apache.axis2.databinding.types.xsd._boolean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.crypto.Mac;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("MacAutoService")
public class MacAutoService {
    @Resource
    private MacAutoDao macAutoDao;
    @Autowired
    MessageService messageService;
    @Resource
    private PositionService positionService;
    @Resource
    private AlarmService alarmService;
    //todo
    //自动查出机器底数出数的数据到mysql表中（待实现）
    public int insertMachine(List<MachineModel> machineModels){
        return macAutoDao.insertMachine(machineModels);
    }

    public int insertManual(List<ManualModel> manualModels) {
        return macAutoDao.insertManual(manualModels);
    }

    public PageInfo getManualList(SimpleRequest request) {
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(request.getQueryBt())){
                param.put("queryBt",request.getQueryBt());
            }
            if(StringUtils.isNotBlank(request.getStartTime())){
                param.put("startTime",request.getStartTime());
            }
            if(StringUtils.isNotBlank(request.getEndTime())){

                param.put("endTime",request.getEndTime());
            }
            List<ManualModel> list = macAutoDao.getManualList(param);
            if(CollectionUtils.isNotEmpty(list) && list.size() > 0){
                for (ManualModel manualModel:list){
                    PositionEntity positionEntity = positionService.getPositionInfoById(manualModel.getPositionCode());
                    if(null != positionEntity) {
                        manualModel.setPositionName(positionEntity.getPositionName());
                    }
                    if(StringUtils.isNotBlank(manualModel.getMlName())){
                        manualModel.setMlName(manualModel.getMlName()+"'");
                    }
                }
            }
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    public PageInfo getMachineList(SimpleRequest<Map> request) {
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(request.getQueryBt())) {
                param.put("queryBt", request.getQueryBt());
            }
            if (StringUtils.isNotBlank(request.getStartTime())) {
                param.put("startTime", request.getStartTime());
            }
            if (StringUtils.isNotBlank(request.getEndTime())) {

                param.put("endTime", request.getEndTime());
            }
            List<MachineModel> list = macAutoDao.getMachineList(param);
            if(CollectionUtils.isNotEmpty(list) && list.size() > 0){
                for (MachineModel machineModel:list){
                    PositionEntity positionEntity = positionService.getPositionInfoById(machineModel.getPositionCode());
                    if(null != positionEntity) {
                        machineModel.setPositionName(positionEntity.getPositionName());
                    }
                    if(StringUtils.isNotBlank(machineModel.getMlName())){
                        machineModel.setMlName(machineModel.getMlName()+"'");
                    }
                }
            }
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    public BaseResponse saveManual(ManualModel manualModel){
        int num = macAutoDao.saveManual(manualModel);
        if(num > 0){
            return BaseResponse.ok("新增成功");
        }else {
            return BaseResponse.fail("新增失败");
        }
    }

    /**
     * 编辑人工底数
     * @param manualModel
     * @return
     */
    public BaseResponse updateManual(ManualModel manualModel){
        int num = macAutoDao.updateManual(manualModel);
        if(num >0){
            return BaseResponse.ok("更新数据成功");
        }else {
            return BaseResponse.fail("更新数据失败");
        }
    }

    /**
     * 删除人工底数
     * @param ids
     * @return
     */
    public BaseResponse deleteManual(List<String> ids){
        int num = macAutoDao.deleteManual(ids);
        if(num >0){
            return BaseResponse.ok("删除数据成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 删除机器底数
     * @param ids
     * @return
     */
    public BaseResponse deleteMachine(List<String> ids){
        int num = macAutoDao.deleteMachine(ids);
        if(num >0){
            return BaseResponse.ok("删除数据成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }




    /**
     * 查询人工以日期为分类数据
     * @param param
     * @return
     */
    public List<AutoBuildEntity> queryManualByDate(Map param){
        return macAutoDao.queryManualByDate(param);
    }
    /**
     * 查询人工以时间点为分类数据
     * @param param
     * @return
     */
    public List<AutoBuildEntity> queryMachineByDate(Map param){
        return macAutoDao.queryMachineByDate(param);
    }


    /**
     * 查询比对标记表数据
     * @param request
     * @return
     */
    public PageInfo queryAutoBuildList(SimpleRequest<Map> request){
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(request.getBuildDate())) {
                param.put("buildDate", request.getBuildDate());
            }
            if(StringUtils.isNotBlank(request.getQueryBt())){
                param.put("queryBt", request.getQueryBt());
            }
            List<AutoBuildEntity> list = macAutoDao.queryAutoBuildList(param);
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    /**
     * 查询比对详情表数据
     * @param request
     * @return
     */
    public PageInfo getAutoDataList(SimpleRequest<Map> request){
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(request.getBuildDate())) {
                param.put("buildDate", request.getBuildDate());
            }
            if (StringUtils.isNotBlank(request.getBuildTime())) {
                param.put("buildTime", request.getBuildTime());
            }
            List<AutoDataEntity> list = macAutoDao.getAutoDataList(param);
            if(CollectionUtils.isNotEmpty(list)){
                for(AutoDataEntity autoDataEntity:list){
                    if(StringUtils.isBlank(autoDataEntity.getMslValue())){
                        autoDataEntity.setMslValue("未匹配");
                    }
                    if(StringUtils.isBlank(autoDataEntity.getTzslValue())){
                        autoDataEntity.setTzslValue("未匹配");
                    }
                    if(StringUtils.isBlank(autoDataEntity.getXxplValue())){
                        autoDataEntity.setXxplValue("未匹配");
                    }
                    if(StringUtils.isBlank(autoDataEntity.getTkplValue())){
                        autoDataEntity.setTkplValue("未匹配");
                    }
                    if(StringUtils.isNotBlank(autoDataEntity.getMlName())){
                        autoDataEntity.setMlName(autoDataEntity.getMlName()+"'");
                    }
                    if(StringUtils.isNotBlank(autoDataEntity.getXtdValue())){
                        BigDecimal c1 = new BigDecimal(autoDataEntity.getXtdValue());
                        BigDecimal oneCl = c1.divide(new BigDecimal(autoDataEntity.getMslValue()).multiply(new BigDecimal(1000)),4);
                        autoDataEntity.setOneCl(oneCl.toString());//策略1
                    }
                    if(StringUtils.isNotBlank(autoDataEntity.getTzdValue())){
                        BigDecimal c2 = new BigDecimal(autoDataEntity.getTzdValue());
                        BigDecimal twoCl = c2.divide(new BigDecimal(autoDataEntity.getMslValue()).multiply(new BigDecimal(1000)),4);
                        autoDataEntity.setTwoCl(twoCl.toString());//策略2
                    }
                }
            }
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    /**
     * 查询人工以日期为分类数据最新一条
     * @return
     */
    public String queryLimitDate(){
        return macAutoDao.queryLimitDate();
    }

    public List<ManualModel> getManualListByDate(String buildDate){
        Map map = new HashMap();
        map.put("buildDate", buildDate);
        return macAutoDao.getManualListByDate(map);
    }

    public List<MachineModel> getMachineListByDate(String buildTime){
        Map map = new HashMap();
        map.put("buildTime", buildTime);
        return macAutoDao.getMachineListByDate(map);
    }

    /**
     * 数据比对业务操作
     */
    public BaseResponse openAuto(SimpleRequest<Map> params){
        //对比某个时间点机器的数据
        //核心是对比下频vs天频；调制速率vs码速率
        //机器的码速率要*1000
        try {
            List<ManualModel> manualModels = this.getManualListByDate(params.getBuildDate());
            List<MachineModel> machineModels = this.getMachineListByDate(params.getBuildTime());
            //用户选择xx时间点的机器底数
            this.getAutoDocker(machineModels, manualModels, "手动");
            return BaseResponse.ok("成功");
        }catch (Exception e){
            return BaseResponse.fail("手动比对失败");
        }

    }

//    /**
//     * 处理比对数据(旧)
//     * @param machineModels
//     * @param manualModels
//     */
//    @Transactional
//    public void getAutoDocker(List<MachineModel> machineModels, List<ManualModel> manualModels, String remark){
//
//        if(CollectionUtils.isEmpty(machineModels)){
//            return;
//        }
//        if(CollectionUtils.isEmpty(manualModels)){
//            return;
//        }
//
//        //查询误差范围
//        BigDecimal paramValue = new BigDecimal(0);
//        if(StringUtils.isNotBlank(macAutoDao.getParamValue())){
//            paramValue = new BigDecimal(macAutoDao.getParamValue());
//        }
//        List<AutoDataEntity> autoDataEntities = new ArrayList<>();
//        List<ManualModel> newManualList = new ArrayList<>();
//        List<MachineModel> newMachineList = new ArrayList<>();
//        //处理数据
//        for(ManualModel manualModel:manualModels){
//            for(MachineModel machineModel:machineModels){
//                AutoDataEntity autoDataEntity = new AutoDataEntity();
//                if(manualModel.getTzfsName().equalsIgnoreCase(machineModel.getTzysName())
//                        && manualModel.getSystemName().equalsIgnoreCase(machineModel.getXhType())
//                        && manualModel.getXdbmCode().concat(manualModel.getMlName()).equalsIgnoreCase(machineModel.getBmType().concat(machineModel.getMlName())))
//                {
//                    autoDataEntity.setSystemName(manualModel.getSystemName());
//                    autoDataEntity.setXhType(machineModel.getXhType());
//                    autoDataEntity.setXzbValue(manualModel.getXzbValue());
//                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
//                    autoDataEntity.setTzfsName(manualModel.getTzfsName());
//                    autoDataEntity.setTzysName(machineModel.getTzysName());
//                    autoDataEntity.setXdbmCode(manualModel.getXdbmCode() + machineModel.getMlName());
//                    autoDataEntity.setBmType(machineModel.getBmType());
//                    autoDataEntity.setMlName(machineModel.getMlName());
//                    autoDataEntity.setBuildDate(manualModel.getBuildDate());
//                    autoDataEntity.setBuildTime(machineModel.getBuildTime());
//                    //判断系统=信号类型 调制方式=调制样式 信号编码=编码类型+码率
//                    BigDecimal xxplValue = new BigDecimal(manualModel.getXxplValue());//下行频率
//                    BigDecimal tkplValue = new BigDecimal(machineModel.getTkplValue());//天空频率
//                    if(xxplValue.compareTo(tkplValue) >= 0){
//                        BigDecimal xtdValue = xxplValue.subtract(tkplValue);//下行频率与天空频率差值
//                        if(xtdValue.compareTo(paramValue) <= 0){
//                            autoDataEntity.setXxplValue(xxplValue.toString());
//                            autoDataEntity.setTkplValue(tkplValue.toString());
//                            autoDataEntity.setXtdValue(xtdValue.toString());
//                        }else {
//                            continue;
//                        }
//                    }else {
//                        BigDecimal xtdValue = tkplValue.subtract(xxplValue);
//                        if(xtdValue.compareTo(paramValue) <= 0){
//                            autoDataEntity.setXxplValue(xxplValue.toString());
//                            autoDataEntity.setTkplValue(tkplValue.toString());
//                            autoDataEntity.setXtdValue("-" + xtdValue);
//                        }else {
//                            continue;
//                        }
//                    }
//                    BigDecimal tzslValue = new BigDecimal(manualModel.getTzslValue());//调制速率
//                    BigDecimal mslValue = new BigDecimal(machineModel.getMslValue());//码速率
//                    if(tzslValue.compareTo(mslValue.multiply(new BigDecimal(1000))) >= 0){
//                        BigDecimal tzdValue = tzslValue.subtract(mslValue.multiply(new BigDecimal(1000)));//调制速率与码速率差值
//                        if(tzdValue.compareTo(paramValue) <= 0){
//                            autoDataEntity.setTzslValue(tzslValue.toString());
//                            autoDataEntity.setMslValue(mslValue.toString());
//                            autoDataEntity.setTzdValue(tzdValue.toString());
//                        }
////                        else if(tzdValue.compareTo(paramValue) == 0){
////                            autoDataEntity.setTzslValue(tzslValue.toString());
////                            autoDataEntity.setMslValue(mslValue.toString());
////                            autoDataEntity.setTzdValue("0");
////                        }
//                        else {
//                            continue;
//                        }
//                    }else {
//                        BigDecimal tzdValue = mslValue.multiply(new BigDecimal(1000)).subtract(tzslValue);//调制速率与码速率差值
//                        if(tzdValue.compareTo(paramValue) <= 0){
//                            autoDataEntity.setTzslValue(tzslValue.toString());
//                            autoDataEntity.setMslValue(mslValue.toString());
//                            autoDataEntity.setTzdValue("-" + tzdValue);
//                        }
////                        else if(tzdValue.compareTo(paramValue) == 0){
////                            autoDataEntity.setTzslValue(tzslValue.toString());
////                            autoDataEntity.setMslValue(mslValue.toString());
////                            autoDataEntity.setTzdValue("0");
////                        }
//                        else {
//                            continue;
//                        }
//                    }
//                }else{
//                    continue;
//                }
//                autoDataEntities.add(autoDataEntity);
//                newManualList.add(manualModel);
//                newMachineList.add(machineModel);
//            }
//        }
//        if(autoDataEntities.size() > 0){//对比数大于0 ，小于零责不存数据
//            AutoBuildEntity buildEntity = new AutoBuildEntity();
//            //保存标记表
//            buildEntity.setBuildDate(autoDataEntities.get(0).getBuildDate());
//            buildEntity.setBuildTime(autoDataEntities.get(0).getBuildTime());
//            buildEntity.setRemark(remark);
//            //先查询看是否存在此时间点的比对数据
//            Map<String, Object> param = new HashMap<>();
//            param.put("buildDate", buildEntity.getBuildDate());
//            param.put("buildTime", buildEntity.getBuildTime());
//            Integer countBuild = macAutoDao.getAutoBuildCount(param);
//            if(countBuild.intValue() > 0){
//                macAutoDao.deleteAutoDateByThis(param);
//               if(  1 != macAutoDao.updateAutoBuild(buildEntity)){
//                   throw new RuntimeException();
//               }
//            }else {
//                if (1 != macAutoDao.insertAutoBuild(buildEntity)) {
//                    throw new RuntimeException();
//                }
//            }
//            //求前后差集
//            List<ManualModel> collectManual = manualModels.stream().filter(item ->
//                    !newManualList.contains(item)).collect(Collectors.toList());
//            List<MachineModel> collectMachine = machineModels.stream().filter(item ->
//                    !newMachineList.contains(item)).collect(Collectors.toList());
//            if(CollectionUtils.isNotEmpty(collectManual)){
//                for (ManualModel manualModel:collectManual){
//                    AutoDataEntity autoDataEntity = new AutoDataEntity();
//                    autoDataEntity.setBuildDate(manualModel.getBuildDate());
//                    autoDataEntity.setBuildTime(autoDataEntities.get(0).getBuildTime());
//                    autoDataEntity.setXxplValue(manualModel.getXxplValue());
//                    autoDataEntity.setSystemName(manualModel.getSystemName());
//                    autoDataEntity.setTzslValue(manualModel.getTzslValue());
//                    autoDataEntity.setTzfsName(manualModel.getTzfsName());
//                    autoDataEntity.setXdbmCode(manualModel.getXdbmCode()+manualModel.getMlName());
//                    autoDataEntity.setXzbValue(manualModel.getXzbValue());
//                    autoDataEntity.setXxslValue(manualModel.getXxslValue());
//                    autoDataEntities.add(autoDataEntity);
//                }
//                MessageEntity messageEntity = new MessageEntity();
//                messageEntity.setUserNo(remark + "校验底数");
//                messageEntity.setTitle(remark+"校验人工底数出现未匹配的底数");
//                messageEntity.setContent(JSON.toJSONString(collectManual));
//                messageEntity.setOperationType(2);
//                messageService.insertMessage(messageEntity);
//            }
//            if(CollectionUtils.isNotEmpty(collectMachine)){
//                for (MachineModel machineModel:collectMachine){
//                    AutoDataEntity autoDataEntity = new AutoDataEntity();
//                    autoDataEntity.setBuildDate(autoDataEntities.get(0).getBuildDate());
//                    autoDataEntity.setBuildTime(machineModel.getBuildTime());
//                    autoDataEntity.setMlName(machineModel.getMlName());
//                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
//                    autoDataEntity.setTkplValue(machineModel.getTkplValue());
//                    autoDataEntity.setXhType(machineModel.getXhType());
//                    autoDataEntity.setMslValue(machineModel.getMslValue());
//                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
//                    autoDataEntity.setTzysName(machineModel.getTzysName());
//                    autoDataEntity.setBmType(machineModel.getBmType());
//                    autoDataEntities.add(autoDataEntity);
//                }
//                MessageEntity messageEntity = new MessageEntity();
//                messageEntity.setUserNo(remark + "校验底数");
//                messageEntity.setTitle(remark+"校验机器底数出现未匹配的底数");
//                messageEntity.setContent(JSON.toJSONString(collectMachine));
//                messageEntity.setOperationType(2);
//                messageService.insertMessage(messageEntity);
//            }
//            if(0 == macAutoDao.insertAutoDatas(autoDataEntities)){
//                throw new RuntimeException();
//            }
//        }
//    }

    /**
     * 处理比对数据(新)
     * @param machineModels
     * @param manualModels
     */
    @Transactional
    public void getAutoDocker(List<MachineModel> machineModels, List<ManualModel> manualModels, String remark){

        if(CollectionUtils.isEmpty(machineModels)){
            return;
        }
        if(CollectionUtils.isEmpty(manualModels)){
            return;
        }

        //查询频率误差范围
        BigDecimal paramValue = new BigDecimal(0);
        if(StringUtils.isNotBlank(macAutoDao.getParamValue("1"))){
            paramValue = new BigDecimal(macAutoDao.getParamValue("1"));
        }
        //查询速率误差范围
        BigDecimal paramValueSl = new BigDecimal(0);
        if(StringUtils.isNotBlank(macAutoDao.getParamValue("2"))){
            paramValueSl = new BigDecimal(macAutoDao.getParamValue("2"));
        }
        List<AutoDataEntity> autoDataEntities = new ArrayList<>();
        List<ManualModel> newManualList = new ArrayList<>();
        List<MachineModel> newMachineList = new ArrayList<>();
        //处理数据
        for(ManualModel manualModel:manualModels){//人工数据循环体
            for(MachineModel machineModel:machineModels){//机器数据循环体
                AutoDataEntity autoDataEntity = new AutoDataEntity();
                    autoDataEntity.setSystemName(manualModel.getSystemName());
                    autoDataEntity.setXhType(machineModel.getXhType());
                    autoDataEntity.setXzbValue(manualModel.getXzbValue());
                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
                    autoDataEntity.setTzfsName(manualModel.getTzfsName());
                    autoDataEntity.setTzysName(machineModel.getTzysName());
                    autoDataEntity.setXdbmCode(manualModel.getXdbmCode()+ " " + machineModel.getMlName());
                    autoDataEntity.setBmType(machineModel.getBmType());
                    autoDataEntity.setMlName(machineModel.getMlName());
                    autoDataEntity.setBuildDate(manualModel.getBuildDate());
                    autoDataEntity.setBuildTime(machineModel.getBuildTime());
                    //判断系统=信号类型 调制方式=调制样式 信号编码=编码类型+码率
                    BigDecimal xxplValue = new BigDecimal(manualModel.getXxplValue());//下行频率
                    BigDecimal tkplValue = new BigDecimal(machineModel.getTkplValue());//天空频率
                    if(xxplValue.compareTo(tkplValue) >= 0){
                        BigDecimal xtdValue = xxplValue.subtract(tkplValue);//下行频率与天空频率差值
                        //计算百分比浮动paramValue 除以100
                        BigDecimal pointValue = xxplValue.multiply(paramValue.divide(new BigDecimal(100))); //上下浮动值
                        if(xtdValue.compareTo(pointValue) <= 0){
                            autoDataEntity.setXxplValue(xxplValue.toString());
                            autoDataEntity.setTkplValue(tkplValue.toString());
                            autoDataEntity.setXtdValue(xtdValue.toString());
                        }else {
                            continue;
                        }
                    }else {
                        BigDecimal xtdValue = tkplValue.subtract(xxplValue);
                        //计算百分比浮动paramValue 除以100
                        BigDecimal pointValue = tkplValue.multiply(paramValue.divide(new BigDecimal(100))); //上下浮动值
                        if(xtdValue.compareTo(pointValue) <= 0){
                            autoDataEntity.setXxplValue(xxplValue.toString());
                            autoDataEntity.setTkplValue(tkplValue.toString());
                            autoDataEntity.setXtdValue("-" + xtdValue);
                        }else {
                            continue;
                        }
                    }
                    BigDecimal tzslValue = new BigDecimal(manualModel.getTzslValue());//调制速率
                    BigDecimal mslValue = new BigDecimal(machineModel.getMslValue());//码速率
                    if(tzslValue.compareTo(mslValue.multiply(new BigDecimal(1000))) >= 0){
                        BigDecimal tzdValue = tzslValue.subtract(mslValue.multiply(new BigDecimal(1000)));//调制速率与码速率差值
                        //计算百分比浮动paramValue 除以100
                        BigDecimal pointValue = tzslValue.multiply(paramValueSl.divide(new BigDecimal(100))); //上下浮动值
                        if(tzdValue.compareTo(pointValue) <= 0){
                            autoDataEntity.setTzslValue(tzslValue.toString());
                            autoDataEntity.setMslValue(mslValue.toString());
                            autoDataEntity.setTzdValue(tzdValue.toString());
                        }
                        else {
                            continue;
                        }
                    }else {
                        BigDecimal tzdValue = mslValue.multiply(new BigDecimal(1000)).subtract(tzslValue);//调制速率与码速率差值
                        //计算百分比浮动paramValue 除以100
                        BigDecimal pointValue = mslValue.multiply(paramValueSl.divide(new BigDecimal(100))); //上下浮动值
                        if(tzdValue.compareTo(pointValue) <= 0){
                            autoDataEntity.setTzslValue(tzslValue.toString());
                            autoDataEntity.setMslValue(mslValue.toString());
                            autoDataEntity.setTzdValue("-" + tzdValue);
                        }
                        else {
                            continue;
                        }
                    }
                autoDataEntities.add(autoDataEntity);
                    if(null != autoDataEntity &&
                            StringUtils.isNotBlank(autoDataEntity.getXxplValue()) &&
                            StringUtils.isNotBlank(autoDataEntity.getTkplValue()) &&
                            StringUtils.isNotBlank(autoDataEntity.getTzslValue()) &&
                            StringUtils.isNotBlank(autoDataEntity.getMslValue())){
                    newManualList.add(manualModel);
                    newMachineList.add(machineModel);
                }
            }
        }

//        if(autoDataEntities.size() > 0){//对比数大于0 ，小于零则不存数据
            AutoBuildEntity buildEntity = new AutoBuildEntity();
            //保存标记表
            buildEntity.setBuildDate(manualModels.get(0).getBuildDate());
            buildEntity.setBuildTime(machineModels.get(0).getBuildTime());
            buildEntity.setRemark(remark);
            //先查询看是否存在此时间点的比对数据
            Map<String, Object> param = new HashMap<>();
            param.put("buildDate", buildEntity.getBuildDate());
            param.put("buildTime", buildEntity.getBuildTime());
            Integer countBuild = macAutoDao.getAutoBuildCount(param);
            if(countBuild.intValue() > 0){
                macAutoDao.deleteAutoDateByThis(param);
                if(  1 != macAutoDao.updateAutoBuild(buildEntity)){
                    throw new RuntimeException();
                }
            }else {
                if (1 != macAutoDao.insertAutoBuild(buildEntity)) {
                    throw new RuntimeException();
                }
            }
            //求前后差集
            List<ManualModel> collectManual = manualModels.stream().filter(item ->
                    !newManualList.contains(item)).collect(Collectors.toList());
            List<MachineModel> collectMachine = machineModels.stream().filter(item ->
                    !newMachineList.contains(item)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(collectManual)){
                for (ManualModel manualModel:collectManual){
                    AutoDataEntity autoDataEntity = new AutoDataEntity();
                    autoDataEntity.setBuildDate(manualModel.getBuildDate());
                    autoDataEntity.setBuildTime(buildEntity.getBuildTime());
                    autoDataEntity.setXxplValue(manualModel.getXxplValue());
                    autoDataEntity.setSystemName(manualModel.getSystemName());
                    autoDataEntity.setTzslValue(manualModel.getTzslValue());
                    autoDataEntity.setTzfsName(manualModel.getTzfsName());
                    autoDataEntity.setXdbmCode(manualModel.getXdbmCode() +" "+ manualModel.getMlName());
                    autoDataEntity.setXzbValue(manualModel.getXzbValue());
                    autoDataEntity.setXxslValue(manualModel.getXxslValue());
                    autoDataEntities.add(autoDataEntity);
                }
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setUserNo(remark + "校验底数");
                messageEntity.setTitle(remark+"校验人工底数出现未匹配的底数");
                messageEntity.setContent(JSON.toJSONString(collectManual));
                messageEntity.setOperationType(2);
                messageService.insertMessage(messageEntity);
                //生成一条告警保存到告警表
                AlarmDataEntity alarmDataEntity = new AlarmDataEntity();
                alarmDataEntity.setAlarmTitle("人工底数融合未匹配告警");
                alarmDataEntity.setAlarmContent(JSON.toJSONString(collectMachine));
                alarmService.insertAlarmData(alarmDataEntity);
            }
            if(CollectionUtils.isNotEmpty(collectMachine)){
                for (MachineModel machineModel:collectMachine){
                    AutoDataEntity autoDataEntity = new AutoDataEntity();
                    autoDataEntity.setBuildDate(buildEntity.getBuildDate());
                    autoDataEntity.setBuildTime(machineModel.getBuildTime());
                    autoDataEntity.setMlName(machineModel.getMlName());
                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
                    autoDataEntity.setTkplValue(machineModel.getTkplValue());
                    autoDataEntity.setXhType(machineModel.getXhType());
                    autoDataEntity.setMslValue(machineModel.getMslValue());
                    autoDataEntity.setZzbValue(machineModel.getZzbValue());
                    autoDataEntity.setTzysName(machineModel.getTzysName());
                    autoDataEntity.setBmType(machineModel.getBmType());
                    autoDataEntities.add(autoDataEntity);
                }
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setUserNo(remark + "校验底数");
                messageEntity.setTitle(remark+"校验机器底数出现未匹配的底数");
                messageEntity.setContent(JSON.toJSONString(collectMachine));
                messageEntity.setOperationType(2);
                messageService.insertMessage(messageEntity);
                //生成一条告警保存到告警表
                AlarmDataEntity alarmDataEntity = new AlarmDataEntity();
                alarmDataEntity.setAlarmTitle("机器底数融合未匹配告警");
                alarmDataEntity.setAlarmContent(JSON.toJSONString(collectMachine));
                alarmService.insertAlarmData(alarmDataEntity);
            }
            if(0 == macAutoDao.insertAutoDatas(autoDataEntities)){
                throw new RuntimeException();
            }
//        }
    }


    public AutoBuildEntity getAutoBuildById(SimpleRequest<Map> params){
        Map<String, Object> param = new HashMap<>();
        param.put("id", params.getQueryBt());
        return macAutoDao.getAutoBuildById(param);
    }

    /**
     * 删除汇总融合数据
     * @param ids
     * @return
     */
    public BaseResponse deleteAutoBuild(List<String> ids){
        List<AutoDataEntity> list = new ArrayList<>();
        //删除融合数据
        if(null != ids && ids.size() > 0) {
            for(String id:ids) {
                Map<String, Object> param = new HashMap<>();
                param.put("id", id);
                AutoBuildEntity autoBuildById = macAutoDao.getAutoBuildById(param);
                Map<String, Object> paramValue = new HashMap<>();
                if(null != autoBuildById){
                    paramValue.put("buildDate", autoBuildById.getBuildDate());
                    paramValue.put("buildTime", autoBuildById.getBuildTime());
                    list.addAll(macAutoDao.getAutoDataList(paramValue));
                }
            }
        }
        int num = macAutoDao.deleteAutoBuild(ids);
        if(num >0){
            List<String> dataIds = new ArrayList<>();
            if(null != list && list.size() > 0){
                for(AutoDataEntity autoDataEntity:list){
                    dataIds.add(String.valueOf(autoDataEntity.getId()));
                }
                if(null != dataIds && dataIds.size() > 0){
                    macAutoDao.deleteAutoData(dataIds);
                }
            }
            return BaseResponse.ok("删除数据成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    public int queryMachineCountByInfo(MachineModel machineModel){
        return macAutoDao.queryMachineCountByInfo(machineModel);
    }
}
