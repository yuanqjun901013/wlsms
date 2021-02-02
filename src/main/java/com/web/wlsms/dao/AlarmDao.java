package com.web.wlsms.dao;

import com.web.wlsms.entity.AlarmConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlarmDao {

   List<AlarmConfigEntity> getAlarmConfig();
   void insertAlarmConfig(AlarmConfigEntity alarmConfigEntity);
}
