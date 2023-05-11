package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.PositionEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"阵地信息配置管理"})
@RestController
@RequestMapping("/admin/position")
public class PositionController {
    @Resource
    private PositionService positionService;

    @ApiOperation("阵地信息查询")
    @PostMapping("getPositionList")
    public Map<String,Object> getPositionList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getPositionList = positionService.getPositionList(params);
            resultMap.put("total", getPositionList.getTotal());
            resultMap.put("rows", getPositionList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 供下拉列表适用
     * @param
     * @return
     */
    @ApiOperation("供下拉列表适用")
    @PostMapping("getPositionArr")
    public Map<String,Object> getPositionArr(HttpServletRequest request){
        SimpleRequest<Map> params = new SimpleRequest<>();
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<PositionEntity> getPositionList = positionService.getPositionArr(params);
            resultMap.put("rows", getPositionList);
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @ApiOperation("保存阵地信息")
    @PostMapping("savePosition")
    public BaseResponse savePosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.savePosition(positionEntity);
    }

    @ApiOperation("编辑阵地信息")
    @PostMapping("updatePosition")
    public BaseResponse updatePosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.updatePosition(positionEntity);
    }

    @ApiOperation("删除阵地信息")
    @PostMapping("destroyPosition")
    public BaseResponse destroyPosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.destroyPosition(positionEntity);
    }

}
