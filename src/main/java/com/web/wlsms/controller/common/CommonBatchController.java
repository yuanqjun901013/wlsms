package com.web.wlsms.controller.common;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.common.CommonBatchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/batch/common")
public class CommonBatchController {
    @Resource
    private CommonBatchService batchService;
    /**
     * 一键删除操作
     * @param
     * @return
     */
    @RequestMapping("deleteBatch")
    public BaseResponse deleteBatch(String batchKey){
        if(null == batchKey){
            return BaseResponse.fail("入参有误，请重试");
        }
        return batchService.deleteBatch(batchKey);
    }

    /**
     * 一键清空操作
     */
    @RequestMapping("getClear")
    public Map<String,Object> getClear(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", 0);
        resultMap.put("rows", "");
        return resultMap;
    }
}
