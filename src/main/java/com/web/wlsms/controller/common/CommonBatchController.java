package com.web.wlsms.controller.common;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.TableEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.common.CommonBatchService;
import com.web.wlsms.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
@Api(tags = {"公共业务管理"})
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
    @ApiOperation("一键删除")
    @PostMapping("deleteBatch")
    public BaseResponse deleteBatch(String batchKey){
        if(null == batchKey){
            return BaseResponse.fail("入参有误，请重试");
        }
        return batchService.deleteBatch(batchKey);
    }

    /**
     * 一键清空操作
     */
    @ApiOperation("一键清空")
    @PostMapping("getClear")
    public Map<String,Object> getClear(SimpleRequest<Map> params){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", 0);
        resultMap.put("rows", "");
        return resultMap;
    }


    /**
     * Excel导出
     * @return
     */
    @ApiOperation("导出相关sql的excel")
    @PostMapping("exportCommon")
    public void exportCommon(SimpleRequest<Map> params, HttpServletRequest request, HttpServletResponse response) {
        TableEntity entity = new TableEntity();
        if (StringUtils.isNotEmpty(params.getQueryBt())) {
            entity.setValues("select * from " + params.getQueryBt() + " order by id desc");
        }
        String destFileName = params.getQueryBt() + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xlsx";
        ExcelUtil.export(request,response,params.getQueryBt() + ".xlsx", destFileName, batchService.queryTableBySql(entity));
    }

}
