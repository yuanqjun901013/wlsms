package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.ParamEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.ParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Api(tags = {"参数管理"})
@RestController
@RequestMapping("/admin/param")
public class ParamController {
    @Resource
    private ParamService paramService;

    @ApiOperation("参数查询")
    @PostMapping("getParametersList")
    public Map<String,Object> getParametersList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getParametersList = paramService.getParametersList(params);
            resultMap.put("total", getParametersList.getTotal());
            resultMap.put("rows", getParametersList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @ApiOperation("参数更新")
    @PostMapping("paramSubmit")
    public BaseResponse paramSubmit(HttpServletRequest request, ParamEntity paramEntity){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == paramEntity){
            return BaseResponse.fail("入参有误");
        }
        if(StringUtils.isBlank(userNo)){
            return BaseResponse.fail("用户没登录");
        }
        return paramService.paramSubmit(paramEntity);
    }
}
