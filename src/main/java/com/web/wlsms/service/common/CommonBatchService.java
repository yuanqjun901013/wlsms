package com.web.wlsms.service.common;

import com.web.wlsms.dao.CommonBatchDao;
import com.web.wlsms.entity.TableEntity;
import com.web.wlsms.response.BaseResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CommonBatchService")
public class CommonBatchService {
    @Resource
    private CommonBatchDao batchDao;
    /**
     * 一键删除操作
     * @param batchKey
     * @return
     */
    public BaseResponse deleteBatch(String batchKey){
        TableEntity entity = new TableEntity();
        entity.setTableName(batchKey);
        entity.setParams("1 = 1");
        int num = batchDao.deleteBatch(entity);
        if(num >0){
            return BaseResponse.ok("一键清空成功");
        }else {
            return BaseResponse.fail("一键清空失败");
        }
    }
}
