package com.web.wlsms.dao;

import com.web.wlsms.entity.AlarmConfigEntity;
import com.web.wlsms.entity.AlarmDataEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmDao {

   List<AlarmConfigEntity> getAlarmConfig();
   void insertAlarmConfig(AlarmConfigEntity alarmConfigEntity);
   void insertAlarmData(AlarmDataEntity alarmDataEntity);
   List<AlarmDataEntity> getAlarmInfoList();
}
