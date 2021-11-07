package com.web.wlsms.service.data;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.DataBuildNewDao;
import com.web.wlsms.dao.MacAutoDao;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.alarm.AlarmService;
import com.web.wlsms.service.system.MessageService;
import com.web.wlsms.service.system.PositionService;
import com.web.wlsms.utils.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("DataBuildNewService")
public class DataBuildNewService {
    @Resource
    private DataBuildNewDao dataBuildNewDao;
    @Resource
    private MacAutoDao macAutoDao;
    @Autowired
    MessageService messageService;
    @Resource
    private PositionService positionService;
    @Resource
    private AlarmService alarmService;
    public int queryMachineCountByInfo(MachineModel machineModel){
        return dataBuildNewDao.queryMachineCountByInfo(machineModel);
    }
    //批量插入机器数据
    public int insertMachine(List<MachineModel> machineModels){
        return dataBuildNewDao.insertMachine(machineModels);
    }

    //批量插入人工数据
    public int insertManual(List<MachineModel> manualModels) {
        return dataBuildNewDao.insertManual(manualModels);
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
            List<MachineModel> list = dataBuildNewDao.getManualList(param);
            if(CollectionUtils.isNotEmpty(list) && list.size() > 0){
                for (MachineModel manualModel:list){
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
            List<MachineModel> list = dataBuildNewDao.getMachineList(param);
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

    public BaseResponse saveManual(MachineModel manualModel){
        int num = dataBuildNewDao.saveManual(manualModel);
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
    public BaseResponse updateManual(MachineModel manualModel){
        int num = dataBuildNewDao.updateManual(manualModel);
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
        int num = dataBuildNewDao.deleteManual(ids);
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
        int num = dataBuildNewDao.deleteMachine(ids);
        if(num >0){
            return BaseResponse.ok("删除数据成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 删除汇总融合数据
     * @param ids
     * @return
     */
    public BaseResponse deleteAutoBuild(List<String> ids){
        List<MachineModel> list = new ArrayList<>();
        //删除融合数据
        if(null != ids && ids.size() > 0) {
            for(String id:ids) {
                Map<String, Object> param = new HashMap<>();
                param.put("id", id);
                AutoBuildEntity autoBuildById = dataBuildNewDao.getAutoBuildById(param);
                Map<String, Object> paramValue = new HashMap<>();
                if(null != autoBuildById){
                    paramValue.put("buildDate", autoBuildById.getBuildDate());
                    paramValue.put("buildTime", autoBuildById.getBuildTime());
                    list.addAll(dataBuildNewDao.getAutoDataList(paramValue));
                }
            }
        }
        int num = dataBuildNewDao.deleteAutoBuild(ids);
        if(num >0){
            List<String> dataIds = new ArrayList<>();
            if(null != list && list.size() > 0){
                for(MachineModel autoDataEntity:list){
                    dataIds.add(String.valueOf(autoDataEntity.getId()));
                }
                if(null != dataIds && dataIds.size() > 0){
                    dataBuildNewDao.deleteAutoData(dataIds);
                }
            }
            return BaseResponse.ok("删除数据成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 查询人工以日期为分类数据最新一条
     * @return
     */
    public String queryLimitDate(){
        return dataBuildNewDao.queryLimitDate();
    }

    public List<MachineModel> getManualListByDate(String buildDate){
        Map map = new HashMap();
        map.put("buildDate", buildDate);
        return dataBuildNewDao.getManualListByDate(map);
    }

    public List<MachineModel> getMachineListByDate(String buildTime){
        Map map = new HashMap();
        map.put("buildTime", buildTime);
        return dataBuildNewDao.getMachineListByDate(map);
    }

    /**
     * 数据比对业务操作
     */
    public BaseResponse openAuto(SimpleRequest<Map> params){
        //对比某个时间点机器的数据
        //核心是对比下频vs天频；调制速率vs码速率
        //机器的码速率要*1000
        try {
            List<MachineModel> manualModels = this.getManualListByDate(params.getBuildDate());
            List<MachineModel> machineModels = this.getMachineListByDate(params.getBuildTime());
            //用户选择xx时间点的机器底数
            this.getAutoDocker(machineModels, manualModels, "手动");
            return BaseResponse.ok("成功");
        }catch (Exception e){
            return BaseResponse.fail("手动比对失败");
        }
    }


    /**
     * 处理比对数据(新)
     * @param machineModels
     * @param manualModels
     */
    @Transactional
    public void getAutoDocker(List<MachineModel> machineModels, List<MachineModel> manualModels, String remark){

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
        List<MachineModel> autoDataEntities = new ArrayList<>();
        List<MachineModel> newManualList = new ArrayList<>();
        List<MachineModel> newMachineList = new ArrayList<>();
        Boolean pl = false;
        Boolean msl = false;
        //处理数据
        for(MachineModel manualModel:manualModels){//人工数据循环体
            for(MachineModel machineModel:machineModels){//机器数据循环体
                MachineModel autoData = new MachineModel();//汇总数据

                //频率对比
                BigDecimal rgTkplValue = new BigDecimal(manualModel.getTkplValue());//人工天空频率
                BigDecimal jqTkplValue = new BigDecimal(machineModel.getTkplValue());//机器天空频率
                if(rgTkplValue.compareTo(jqTkplValue) >= 0){
                    BigDecimal xtdValue = rgTkplValue.subtract(jqTkplValue);//人工与机器天空频率差值
                    //计算百分比浮动paramValue 除以100
                    BigDecimal pointValue = rgTkplValue.multiply(paramValue.divide(new BigDecimal(100))); //上下浮动值
                    if(xtdValue.compareTo(pointValue) <= 0){//符合融合值
                        pl = true;
                    }
                }else {
                    BigDecimal xtdValue = jqTkplValue.subtract(rgTkplValue);//机器与人工天空频率差值
                    //计算百分比浮动paramValue 除以100
                    BigDecimal pointValue = jqTkplValue.multiply(paramValue.divide(new BigDecimal(100))); //上下浮动值
                    if(xtdValue.compareTo(pointValue) <= 0){
                        pl = true;
                    }
                }

                //码速率对比
                BigDecimal rgMslValue = new BigDecimal(machineModel.getMslValue());//人工码速率
                BigDecimal jqMslValue = new BigDecimal(machineModel.getMslValue());//机器码速率
                if(rgMslValue.compareTo(jqMslValue) >= 0){
                    BigDecimal tzdValue = rgMslValue.subtract(jqMslValue);//人工和机器码速率差值
                    //计算百分比浮动paramValue 除以100
                    BigDecimal pointValue = rgMslValue.multiply(paramValueSl.divide(new BigDecimal(100))); //上下浮动值
                    if(tzdValue.compareTo(pointValue) <= 0){
                        msl = true;
                    }
                }else {
                    BigDecimal tzdValue = jqMslValue.subtract(rgMslValue);//机器和人工码速率差值
                    //计算百分比浮动paramValue 除以100
                    BigDecimal pointValue = jqMslValue.multiply(paramValueSl.divide(new BigDecimal(100))); //上下浮动值
                    if(tzdValue.compareTo(pointValue) <= 0){
                        msl = true;
                    }
                }

                //赋值
                if(pl && msl){
                    autoData = machineModel;
                    autoData.setBuildDate(manualModel.getBuildDate());
                    autoData.setTitleOs("已融合");
                    autoDataEntities.add(autoData);
                    newManualList.add(manualModel);
                    newMachineList.add(machineModel);
                }
            }
        }

        //循环外

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
            dataBuildNewDao.deleteAutoDataByThis(param);
            if(  1 != macAutoDao.updateAutoBuild(buildEntity)){
                throw new RuntimeException();
            }
        }else {
            if (1 != macAutoDao.insertAutoBuild(buildEntity)) {
                throw new RuntimeException();
            }
        }
        //求前后差集
        List<MachineModel> collectManual = manualModels.stream().filter(item ->
                !newManualList.contains(item)).collect(Collectors.toList());
        List<MachineModel> collectMachine = machineModels.stream().filter(item ->
                !newMachineList.contains(item)).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(collectManual)){
            for (MachineModel manualModel:collectManual){
                MachineModel autoDataEntity = manualModel;
                autoDataEntity.setBuildDate(manualModel.getBuildDate());
                autoDataEntity.setBuildTime(buildEntity.getBuildTime());
                autoDataEntity.setTitleOs("未融合");
                autoDataEntity.setBuildType("人工数据");
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
                MachineModel autoDataEntity = machineModel;
                autoDataEntity.setBuildDate(machineModel.getBuildDate());
                autoDataEntity.setBuildTime(buildEntity.getBuildTime());
                autoDataEntity.setTitleOs("未融合");
                autoDataEntity.setBuildType("机器数据");
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
        if(0 == dataBuildNewDao.insertAutoDatas(autoDataEntities)){
            throw new RuntimeException();
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
     * 查询机器以时间点为分类数据
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

    public AutoBuildEntity getAutoBuildById(SimpleRequest<Map> params){
        Map<String, Object> param = new HashMap<>();
        param.put("id", params.getQueryBt());
        return macAutoDao.getAutoBuildById(param);
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
            List<MachineModel> list = dataBuildNewDao.getAutoDataList(param);
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

}
