package com.web.wlsms.dao;

import com.web.wlsms.entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskDao {

    List<TaskInfo> getTaskInfoList(Map<String, Object> param);

}