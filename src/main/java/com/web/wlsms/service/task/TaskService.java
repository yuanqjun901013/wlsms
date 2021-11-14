package com.web.wlsms.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.TaskDao;
import com.web.wlsms.dao.UserDao;
import com.web.wlsms.entity.PositionEntity;
import com.web.wlsms.entity.TaskInfo;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.PositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TaskService")
public class TaskService {
    @Resource
    private TaskDao taskDao;
    @Resource
    private PositionService positionService;
    @Resource
    private UserDao userDao;
    /**
     * 我发起的任务
     *
     * @param request
     * @return
     */
    public PageInfo getMyPulseTask(SimpleRequest request){
        Map<String, Object> param = new HashMap<>();
        if(StringUtils.isNotBlank(request.getQueryBt())){
            param.put("queryBt",request.getQueryBt());
        }
        if(StringUtils.isNotBlank(request.getStartTime())){
            param.put("startTime",request.getStartTime());
        }
        if(StringUtils.isNotBlank(request.getEndTime())){
            param.put("endTime",request.getEndTime());
        }
        if(StringUtils.isNotBlank(request.getUserNo())){
            param.put("userNo",request.getUserNo());
        }
        if(null != request.getTaskType()){
            param.put("taskType", request.getTaskType());
        }
        if(null != request.getState()){
            param.put("state", request.getState());
        }
        PageHelper.startPage(request.getPage(), request.getRows());
        List<TaskInfo> taskInfoList = taskDao.getTaskInfoList(param);
        if(!CollectionUtils.isEmpty(taskInfoList)){
            for (TaskInfo taskInfo:taskInfoList){
                if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                    taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
                }
                if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                    taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
                }
                if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                    taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
                }
                if(taskInfo.getTaskType().intValue() == 1){
                    taskInfo.setTaskTypeName("数据上报");
                }else if(taskInfo.getTaskType().intValue() == 2){
                    taskInfo.setTaskTypeName("日常调度任务");
                }else {
                    taskInfo.setTaskTypeName("侦测任务");
                }
                if(taskInfo.getState().intValue() == 1){
                    taskInfo.setStateName("待认领");
                }else if(taskInfo.getState().intValue() == 2){
                    taskInfo.setStateName("已接受");
                }else if(taskInfo.getState().intValue() == 3){
                    taskInfo.setStateName("已拒绝");
                }else {
                    taskInfo.setStateName("已完成");
                }
            }
        }
        return new PageInfo<>(taskInfoList);
    }
    /**
     * 待认领的任务
     *
     * @param request
     * @return
     */
    public PageInfo getWaitingTask(SimpleRequest request){
        Map<String, Object> param = new HashMap<>();
        param.put("state", 1);
        if(StringUtils.isNotBlank(request.getQueryBt())){
            param.put("queryBt",request.getQueryBt());
        }
        if(StringUtils.isNotBlank(request.getStartTime())){
            param.put("startTime",request.getStartTime());
        }
        if(StringUtils.isNotBlank(request.getEndTime())){
            param.put("endTime",request.getEndTime());
        }
        if(null != request.getTaskType()){
            param.put("taskType", request.getTaskType());
        }
        PageHelper.startPage(request.getPage(), request.getRows());
        List<TaskInfo> taskInfoList = taskDao.getTaskInfoList(param);
        if(!CollectionUtils.isEmpty(taskInfoList)){
            for (TaskInfo taskInfo:taskInfoList){
                if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                    taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
                }
                if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                    taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
                }
                if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                    taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
                }
                if(taskInfo.getTaskType().intValue() == 1){
                    taskInfo.setTaskTypeName("数据上报");
                }else if(taskInfo.getTaskType().intValue() == 2){
                    taskInfo.setTaskTypeName("日常调度任务");
                }else {
                    taskInfo.setTaskTypeName("侦测任务");
                }
                if(taskInfo.getState().intValue() == 1){
                    taskInfo.setStateName("待认领");
                }else if(taskInfo.getState().intValue() == 2){
                    taskInfo.setStateName("已接受");
                }else if(taskInfo.getState().intValue() == 3){
                    taskInfo.setStateName("已拒绝");
                }else {
                    taskInfo.setStateName("已完成");
                }
            }
        }
        return new PageInfo<>(taskInfoList);
    }
    /**
     * 我认领待办的任务
     *
     * @param request
     * @return
     */
    public PageInfo getTodoTask(SimpleRequest request){
        Map<String, Object> param = new HashMap<>();
        param.put("state", 2);
        if(StringUtils.isNotBlank(request.getQueryBt())){
            param.put("queryBt",request.getQueryBt());
        }
        if(StringUtils.isNotBlank(request.getStartTime())){
            param.put("startTime",request.getStartTime());
        }
        if(StringUtils.isNotBlank(request.getEndTime())){
            param.put("endTime",request.getEndTime());
        }
        if(StringUtils.isNotBlank(request.getUserNo())){
            param.put("receiverUserNo",request.getUserNo());
        }
        if(null != request.getTaskType()){
            param.put("taskType", request.getTaskType());
        }
        PageHelper.startPage(request.getPage(), request.getRows());
        List<TaskInfo> taskInfoList = taskDao.getTaskInfoList(param);
        if(!CollectionUtils.isEmpty(taskInfoList)){
            for (TaskInfo taskInfo:taskInfoList){
                if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                    taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
                }
                if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                    taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
                }
                if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                    taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
                }
                if(taskInfo.getTaskType().intValue() == 1){
                    taskInfo.setTaskTypeName("数据上报");
                }else if(taskInfo.getTaskType().intValue() == 2){
                    taskInfo.setTaskTypeName("日常调度任务");
                }else {
                    taskInfo.setTaskTypeName("侦测任务");
                }
                if(taskInfo.getState().intValue() == 1){
                    taskInfo.setStateName("待认领");
                }else if(taskInfo.getState().intValue() == 2){
                    taskInfo.setStateName("已接受");
                }else if(taskInfo.getState().intValue() == 3){
                    taskInfo.setStateName("已拒绝");
                }else {
                    taskInfo.setStateName("已完成");
                }
            }
        }
        return new PageInfo<>(taskInfoList);
    }
    /**
     * 我的已办理
     *
     * @param request
     * @return
     */
    public PageInfo getEndTask(SimpleRequest request){
        Map<String, Object> param = new HashMap<>();
        param.put("stateStart", 2);
        if(StringUtils.isNotBlank(request.getQueryBt())){
            param.put("queryBt",request.getQueryBt());
        }
        if(StringUtils.isNotBlank(request.getStartTime())){
            param.put("startTime",request.getStartTime());
        }
        if(StringUtils.isNotBlank(request.getEndTime())){
            param.put("endTime",request.getEndTime());
        }
        if(StringUtils.isNotBlank(request.getUserNo())){
            param.put("receiverUserNo",request.getUserNo());
        }
        if(StringUtils.isNotBlank(request.getUserNo())){
            param.put("feedbackUserNo",request.getUserNo());
        }
        if(null != request.getTaskType()){
            param.put("taskType", request.getTaskType());
        }
        if(null != request.getState()){
            param.put("state", request.getState());
        }
        PageHelper.startPage(request.getPage(), request.getRows());
        List<TaskInfo> taskInfoList = taskDao.getTaskInfoList(param);
        if(!CollectionUtils.isEmpty(taskInfoList)){
            for (TaskInfo taskInfo:taskInfoList){
                if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                    taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
                }
                if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                    taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
                }
                if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                    taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
                }
                if(taskInfo.getTaskType().intValue() == 1){
                    taskInfo.setTaskTypeName("数据上报");
                }else if(taskInfo.getTaskType().intValue() == 2){
                    taskInfo.setTaskTypeName("日常调度任务");
                }else {
                    taskInfo.setTaskTypeName("侦测任务");
                }
                if(taskInfo.getState().intValue() == 1){
                    taskInfo.setStateName("待认领");
                }else if(taskInfo.getState().intValue() == 2){
                    taskInfo.setStateName("已接受");
                }else if(taskInfo.getState().intValue() == 3){
                    taskInfo.setStateName("已拒绝");
                }else {
                    taskInfo.setStateName("已完成");
                }
            }
        }
        return new PageInfo<>(taskInfoList);
    }
    /**
     * 所有任务
     *
     * @param request
     * @return
     */
    public PageInfo getAllTask(SimpleRequest request){
        Map<String, Object> param = new HashMap<>();
        if(StringUtils.isNotBlank(request.getQueryBt())){
            param.put("queryBt",request.getQueryBt());
        }
        if(StringUtils.isNotBlank(request.getStartTime())){
            param.put("startTime",request.getStartTime());
        }
        if(StringUtils.isNotBlank(request.getEndTime())){
            param.put("endTime",request.getEndTime());
        }
        if(null != request.getTaskType()){
            param.put("taskType", request.getTaskType());
        }
        if(null != request.getState()){
            param.put("state", request.getState());
        }
        PageHelper.startPage(request.getPage(), request.getRows());
        List<TaskInfo> taskInfoList = taskDao.getTaskInfoList(param);
        if(!CollectionUtils.isEmpty(taskInfoList)){
            for (TaskInfo taskInfo:taskInfoList){
                if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                    taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
                }
                if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                    taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
                }
                if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                    taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
                }
                if(taskInfo.getTaskType().intValue() == 1){
                    taskInfo.setTaskTypeName("数据上报");
                }else if(taskInfo.getTaskType().intValue() == 2){
                    taskInfo.setTaskTypeName("日常调度任务");
                }else {
                    taskInfo.setTaskTypeName("侦测任务");
                }
                if(taskInfo.getState().intValue() == 1){
                    taskInfo.setStateName("待认领");
                }else if(taskInfo.getState().intValue() == 2){
                    taskInfo.setStateName("已接受");
                }else if(taskInfo.getState().intValue() == 3){
                    taskInfo.setStateName("已拒绝");
                }else {
                    taskInfo.setStateName("已完成");
                }
            }
        }
        return new PageInfo<>(taskInfoList);
    }

    /**
     * 获取位置名称
     * @param positionCode
     * @return
     */
    private String getPositionName(String positionCode){
        PositionEntity positionEntity = positionService.getPositionInfoById(positionCode);
        if(null != positionEntity) {
            return positionEntity.getPositionName();
        }
        return "";
    }

    private String getUserName(String userNo){
        UserEntity userEntity = userDao.selectUserByUserNo(userNo);
        if(null != userEntity){
            return userEntity.getUserName();
        }
        return "";
    }

    public BaseResponse getTaskDetail(String id){
        TaskInfo taskInfo = taskDao.getTaskDetail(id);
        if(null != taskInfo){
            if(StringUtils.isNotBlank(taskInfo.getPositionCode())){
                taskInfo.setPositionName(getPositionName(taskInfo.getPositionCode()));
            }
            if(StringUtils.isNotBlank(taskInfo.getReceiverUserNo())){
                taskInfo.setReceiverUserName(getUserName(taskInfo.getReceiverUserNo()));
            }
            if(StringUtils.isNotBlank(taskInfo.getFeedbackUserNo())){
                taskInfo.setFeedbackUserName(getUserName(taskInfo.getFeedbackUserNo()));
            }
            if(taskInfo.getTaskType().intValue() == 1){
                taskInfo.setTaskTypeName("数据上报");
            }else if(taskInfo.getTaskType().intValue() == 2){
                taskInfo.setTaskTypeName("日常调度任务");
            }else {
                taskInfo.setTaskTypeName("侦测任务");
            }
            if(taskInfo.getState().intValue() == 1){
                taskInfo.setStateName("待认领");
            }else if(taskInfo.getState().intValue() == 2){
                taskInfo.setStateName("已接受");
            }else if(taskInfo.getState().intValue() == 3){
                taskInfo.setStateName("已拒绝");
            }else {
                taskInfo.setStateName("已完成");
            }
            return BaseResponse.ok(taskInfo);
        }else {
            return BaseResponse.fail("数据为空");
        }
    }

    public BaseResponse saveTask(TaskInfo taskInfo){
        Integer num = taskDao.saveTask(taskInfo);
        if(num.intValue() >0){
            return BaseResponse.ok("下发任务成功");
        }else {
            return BaseResponse.fail("下发任务失败");
        }
    }

    public BaseResponse updateTask(TaskInfo taskInfo){
        Integer num = taskDao.updateTask(taskInfo);
        if(num.intValue() >0){
            return BaseResponse.ok("更新任务信息成功");
        }else {
            return BaseResponse.fail("更新任务信息失败");
        }
    }

    public BaseResponse deleteTask(String id){
        Integer num = taskDao.deleteTask(id);
        if(num.intValue() >0){
            return BaseResponse.ok("删除任务成功");
        }else {
            return BaseResponse.fail("删除任务失败");
        }
    }

    public BaseResponse receiverTask(TaskInfo taskInfo){
        Integer num = taskDao.receiverTask(taskInfo);
        if(num.intValue() >0){
            return BaseResponse.ok("认领任务成功");
        }else {
            return BaseResponse.fail("认领任务失败");
        }
    }

    public BaseResponse offReceiverTask(TaskInfo taskInfo){
        Integer num = taskDao.offReceiverTask(taskInfo);
        if(num.intValue() >0){
            return BaseResponse.ok("取消认领成功");
        }else {
            return BaseResponse.fail("取消认领失败");
        }
    }

}
