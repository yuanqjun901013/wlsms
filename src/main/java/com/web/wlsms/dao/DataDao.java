package com.web.wlsms.dao;

import com.web.wlsms.entity.DataEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataDao {

   List<DataEntity> getDataList();
   void insertData(DataEntity dataEntity);
}
