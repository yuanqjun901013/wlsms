package com.web.wlsms.service.data;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MacAutoDao;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("MacAutoService")
public class MacAutoService {
    @Resource
    private MacAutoDao macAutoDao;
    //todo
    //自动查出机器底数出数的数据到mysql表中（待实现）
    public int insertMachine(List<MachineModel> machineModels){
        return macAutoDao.insertMachine(machineModels);
    }

    public int insertManual(List<ManualModel> manualModels) {
        return macAutoDao.insertManual(manualModels);
    }

    public PageInfo getManualList(SimpleRequest request) {
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(request.getQueryBt())){
                param.put("queryBt",request.getQueryBt());
            }
            if(StringUtils.isNotBlank(request.getStartTime())){
                param.put("startTime",request.getStartTime());
            }
            if(StringUtils.isNotBlank(request.getEndTime())){

                param.put("endTime",request.getEndTime());
            }
            List<ManualModel> list = macAutoDao.getManualList(param);
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    public PageInfo getMachineList(SimpleRequest<Map> request) {
        PageHelper.startPage(request.getPage(), request.getRows());
        Map<String,Object> param = new HashMap<>();
        try {
            if (StringUtils.isNotBlank(request.getQueryBt())) {
                param.put("queryBt", request.getQueryBt());
            }
            if (StringUtils.isNotBlank(request.getStartTime())) {
                param.put("startTime", request.getStartTime());
            }
            if (StringUtils.isNotBlank(request.getEndTime())) {

                param.put("endTime", request.getEndTime());
            }
            List<MachineModel> list = macAutoDao.getMachineList(param);
            return new PageInfo<>(list);
        }catch (Exception e){
            return new PageInfo();
        }
    }

    public BaseResponse saveManual(ManualModel manualModel){
        int num = macAutoDao.saveManual(manualModel);
        if(num > 0){
            return BaseResponse.ok("新增成功");
        }else {
            return BaseResponse.fail("新增失败");
        }
    }

    /**
     * 编辑人工底数
     * @param manualModel
     * @return
     */
    public BaseResponse updateManual(ManualModel manualModel){
        int num = macAutoDao.updateManual(manualModel);
        if(num >0){
            return BaseResponse.ok("更新数据成功");
        }else {
            return BaseResponse.fail("更新数据失败");
        }
    }

    /**
     * 删除人工底数
     * @param manualModel
     * @return
     */
    public BaseResponse deleteManual(ManualModel manualModel){
        int num = macAutoDao.deleteManual(manualModel);
        if(num >0){
            return BaseResponse.ok("删除信息成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 删除机器底数
     * @param machineModel
     * @return
     */
    public BaseResponse deleteMachine(MachineModel machineModel){
        int num = macAutoDao.deleteMachine(machineModel);
        if(num >0){
            return BaseResponse.ok("删除信息成功");
        }else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 数据比对业务操作
     */
    public void dataAutoBatch(){
        //对比某个时间点机器的数据
        //核心是对比下频vs天频；调制速率vs码速率
        //机器的码速率要*1000
        List<MachineModel> machineModels = new ArrayList<>();
        List<ManualModel> manualModels = new ArrayList<>();
        //用户选择xx时间点的机器底数

    }

}
