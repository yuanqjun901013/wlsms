package com.web.wlsms.dao;

import com.web.wlsms.entity.DataEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataDao {

   List<DataEntity> getDataList(Map map);
   void insertData(DataEntity dataEntity);
}
