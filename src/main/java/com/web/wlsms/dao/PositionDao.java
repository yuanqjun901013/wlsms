package com.web.wlsms.dao;

import com.web.wlsms.entity.PositionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionDao {

   List<PositionEntity> getPositionList(String keyWord);
   List<PositionEntity> getPositionListByPo(String positionCode);
   int insertPosition(PositionEntity positionEntity);
   int updatePosition(PositionEntity positionEntity);
   int destroyPosition(PositionEntity positionEntity);
   PositionEntity getPositionInfoById(String id);
}
