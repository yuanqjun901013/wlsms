package com.web.wlsms.service.data;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MacAutoDao;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.SimpleRequest;
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

    /**
     * 数据比对业务操作
     */
    public void dataAutoBatch(){
        //对比某个时间点机器的数据
        //核心是对比下频vs天频；调制速率vs码速率
        //机器的码速率要*1000
        List<MachineDataModel> machineDataModels = new ArrayList<>();
        List<ManualDataModel> manualDataModels = new ArrayList<>();
        //用户选择xx时间点的机器底数

    }

}
