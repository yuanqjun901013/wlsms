package com.web.wlsms.dao;

import com.web.wlsms.entity.DataEntity;
import com.web.wlsms.entity.MachineDataModel;
import com.web.wlsms.entity.ManualDataModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataDao {

   List<DataEntity> getDataList(Map map);
   List<ManualDataModel> getManualDataList(Map map);
   List<MachineDataModel> getMachineDataList(Map map);
   void insertData(DataEntity dataEntity);
   int insertManualData(List<ManualDataModel> manualDataModels);
   int insertMachineData(List<MachineDataModel> machineDataModels);
}
