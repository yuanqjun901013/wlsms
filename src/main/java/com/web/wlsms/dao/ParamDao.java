package com.web.wlsms.dao;

import com.web.wlsms.entity.ParamEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParamDao {

   List<ParamEntity> getParametersList();
   void insertParam(ParamEntity messageEntity);
}
