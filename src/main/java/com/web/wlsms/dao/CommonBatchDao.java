package com.web.wlsms.dao;

import com.web.wlsms.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface CommonBatchDao {
    int deleteBatch(TableEntity entity);
    List<Map<String, Object>> queryTableBySql(TableEntity entity);

}
