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
   int deleteManual(ManualModel manualModel);
   int deleteMachine(MachineModel machineModel);
   List<AutoBuildEntity> queryManualByDate(Map<String, Object> param);
   List<AutoBuildEntity> queryMachineByDate(Map<String, Object> param);
   List<AutoBuildEntity> queryAutoBuildList(Map<String, Object> param);
   List<AutoDataEntity> getAutoDataList(Map<String, Object> param);
}
