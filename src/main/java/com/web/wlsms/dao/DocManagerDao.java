package com.web.wlsms.dao;

import com.web.wlsms.entity.DocManagerEntity;
import com.web.wlsms.request.UpLoadRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DocManagerDao {

   /**
    * 查询资料资源列表
    * @param keyWord
    * @return
    */
   List<DocManagerEntity> getDocList(String keyWord);

   int insertDoc(UpLoadRequest upLoadRequest);
}
