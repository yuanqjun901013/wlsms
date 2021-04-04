package com.web.wlsms.dao;

import com.web.wlsms.entity.PositionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionDao {

   List<PositionEntity> getPositionList(String keyWord);
   void insertPosition(PositionEntity positionEntity);
}
