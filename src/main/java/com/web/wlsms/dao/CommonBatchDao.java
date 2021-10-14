package com.web.wlsms.dao;

import com.web.wlsms.entity.TableEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CommonBatchDao {
    int deleteBatch(TableEntity entity);
}
