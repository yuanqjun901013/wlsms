package com.web.wlsms.dao;

import com.web.wlsms.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MacAutoDao {
   int insertManual(List<ManualModel> manualModels);
   int insertMachine(List<MachineModel> machineModels);
   List<ManualModel> getManualList(Map map);
   List<MachineModel> getMachineList(Map map);
   int saveManual(ManualModel manualModel);
   int updateManual(ManualModel manualModel);
   int deleteManual(List<String> ids);
   int deleteMachine(List<String> ids);
   int deleteAutoBuild(List<String> ids);
   int deleteAutoData(List<String> ids);
   List<AutoBuildEntity> queryManualByDate(Map<String, Object> param);
   List<AutoBuildEntity> queryMachineByDate(Map<String, Object> param);
   List<AutoBuildEntity> queryAutoBuildList(Map<String, Object> param);
   List<AutoDataEntity> getAutoDataList(Map<String, Object> param);
   String queryLimitDate();
   List<ManualModel> getManualListByDate(Map<String, Object> param);
   List<MachineModel> getMachineListByDate(Map<String, Object> param);
   String getParamValue();
   Integer getAutoBuildCount(Map<String, Object> param);
   int updateAutoBuild(AutoBuildEntity buildEntity);
   int insertAutoBuild(AutoBuildEntity buildEntity);
   int insertAutoDatas(List<AutoDataEntity> autoDatas);
   int deleteAutoDateByThis(Map<String, Object> param);
   AutoBuildEntity getAutoBuildById(Map<String, Object> param);
   int queryMachineCountByInfo(MachineModel machineModel);
}
