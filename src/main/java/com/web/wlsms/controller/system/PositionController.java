package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.PositionEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.PositionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/position")
public class PositionController {
    @Resource
    private PositionService positionService;

    @RequestMapping("getPositionList")
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

    @RequestMapping("savePosition")
    public BaseResponse savePosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.savePosition(positionEntity);
    }

    @RequestMapping("updatePosition")
    public BaseResponse updatePosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.updatePosition(positionEntity);
    }

    @RequestMapping("destroyPosition")
    public BaseResponse destroyPosition(PositionEntity positionEntity){
        if(null == positionEntity){
            return BaseResponse.fail("入参有误，请重试");
        }
        return positionService.destroyPosition(positionEntity);
    }

}
