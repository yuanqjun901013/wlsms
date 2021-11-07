package com.web.wlsms.dao;

import com.web.wlsms.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataBuildNewDao {
    int queryMachineCountByInfo(MachineModel machineModel);
    int insertManual(List<MachineModel> manualModels);
    int insertMachine(List<MachineModel> machineModels);
    List<MachineModel> getManualList(Map map);
    List<MachineModel> getMachineList(Map map);
    int deleteManual(List<String> ids);
    int deleteMachine(List<String> ids);
    int deleteAutoBuild(List<String> ids);
    int deleteAutoData(List<String> ids);
    int saveManual(MachineModel manualModel);
    int updateManual(MachineModel manualModel);
    String queryLimitDate();
    List<MachineModel> getManualListByDate(Map<String, Object> param);
    List<MachineModel> getMachineListByDate(Map<String, Object> param);
    AutoBuildEntity getAutoBuildById(Map<String, Object> param);
    List<MachineModel> getAutoDataList(Map<String, Object> param);
    int insertAutoDatas(List<MachineModel> autoDatas);
    int deleteAutoDataByThis(Map<String, Object> param);
    List<AutoBuildEntity> queryManualByDate(Map<String, Object> param);
    List<AutoBuildEntity> queryMachineByDate(Map<String, Object> param);
}
