package com.web.wlsms.controller.task;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.TaskInfo;
import com.web.wlsms.entity.TaskStateEntity;
import com.web.wlsms.entity.TaskTypeEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
@Api(tags = {"任务管理"})
@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    @Resource
    private TaskService taskService;

    /**
     * 我发起的任务
     *
     * @param params
     * @return
     */
    @ApiOperation("我发起的任务")
    @PostMapping("/getMyPulseTask")
    public Map<String,Object> getMyPulseTask(HttpServletRequest request, SimpleRequest params) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  taskService.getMyPulseTask(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("TaskController&&getMyPulseTask is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 待认领的任务
     *
     * @param params
     * @return
     */
    @ApiOperation("待认领的任务")
    @PostMapping("/getWaitingTask")
    public Map<String,Object> getWaitingTask(HttpServletRequest request, SimpleRequest params) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  taskService.getWaitingTask(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("TaskController&&getWaitingTask is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 我认领待办的任务
     *
     * @param params
     * @return
     */
    @ApiOperation("我认领待办的任务")
    @PostMapping("/getTodoTask")
    public Map<String,Object> getTodoTask(SimpleRequest params, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  taskService.getTodoTask(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("TaskController&&getTodoTask is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }


    /**
     * 我的已办理
     *
     * @param params
     * @return
     */
    @ApiOperation("我的已办理")
    @PostMapping("/getEndTask")
    public Map<String,Object> getEndTask(SimpleRequest params, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  taskService.getEndTask(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("TaskController&&getEndTask is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 所有任务
     *
     * @param params
     * @return
     */
    @ApiOperation("所有任务")
    @PostMapping("/getAllTask")
    public Map<String,Object> getAllTask(SimpleRequest params, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        params.setUserNo(userNo);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  taskService.getAllTask(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("TaskController&&getAllTask is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @ApiOperation("任务详情")
    @PostMapping("getTaskDetail")
    @ResponseBody
    public BaseResponse getTaskDetail(HttpServletRequest request,String id){
        if(StringUtils.isBlank(id)){
            return BaseResponse.fail("任务编码为空");
        }
        return taskService.getTaskDetail(id);
    }

    /**
     * 管理员批量删除任务
     * @param
     * @return
     */
    @ApiOperation("管理员批量删除任务")
    @PostMapping("deleteBatch")
    public BaseResponse deleteBatch(String ids){
        if(null == ids || StringUtils.isBlank(ids)){
            return BaseResponse.fail("入参有误，请重试");
        }
        List<String> idsArr = Arrays.asList(ids.split(","));
        return taskService.deleteBatch(idsArr);
    }

    /**
     * 保存下发任务
     * @param request
     * @param taskInfo
     * @return
     */
    @ApiOperation("保存下发任务")
    @PostMapping("saveTask")
    @ResponseBody
    public BaseResponse saveTask(HttpServletRequest request, TaskInfo taskInfo){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == taskInfo){
            return BaseResponse.fail("新增任务数据为空");
        }
        if(StringUtils.isBlank(taskInfo.getTitle()) || StringUtils.isBlank(taskInfo.getContent())){
            return BaseResponse.fail("任务标题或内容为空");
        }
        if(null == taskInfo.getTaskType()){
            return BaseResponse.fail("任务类型为空");
        }
        if(null == taskInfo.getPositionCode()){
            return BaseResponse.fail("地址为空");
        }
        taskInfo.setUserNo(userNo);
        return taskService.saveTask(taskInfo);
    }


    /**
     * 编辑更新任务
      * @param request
     * @param taskInfo
     * @return
     */
    @ApiOperation("编辑更新任务")
    @PostMapping("updateTask")
    @ResponseBody
    public BaseResponse updateTask(HttpServletRequest request, TaskInfo taskInfo){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == taskInfo){
            return BaseResponse.fail("更新数据为空");
        }
        if(StringUtils.isBlank(taskInfo.getTitle()) || StringUtils.isBlank(taskInfo.getContent())){
            return BaseResponse.fail("任务标题或内容为空");
        }
        taskInfo.setUserNo(userNo);
        return taskService.updateTask(taskInfo);
    }


    /**
     * 删除任务
     * @param request
     * @param id
     * @return
     */
    @ApiOperation("删除任务")
    @PostMapping("deleteTask")
    @ResponseBody
    public BaseResponse deleteTask(HttpServletRequest request, String id){
        if(StringUtils.isBlank(id)){
            return BaseResponse.fail("任务编码为空");
        }
        return taskService.deleteTask(id);
    }
    /**
     * 认领任务
     * @param request
     * @param id
     * @return
     */
    @ApiOperation("认领任务")
    @PostMapping("receiverTask")
    @ResponseBody
    public BaseResponse receiverTask(HttpServletRequest request, String id){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(StringUtils.isBlank(id)){
            return BaseResponse.fail("任务编码为空");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setReceiverUserNo(userNo);
        taskInfo.setId(Long.valueOf(id));
        return taskService.receiverTask(taskInfo);
    }

    /**
     * 取消认领任务
     * @param request
     * @param id
     * @return
     */
    @ApiOperation("取消认领任务")
    @PostMapping("offReceiverTask")
    @ResponseBody
    public BaseResponse offReceiverTask(HttpServletRequest request, String id){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(StringUtils.isBlank(id)){
            return BaseResponse.fail("任务编码为空");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setReceiverUserNo(userNo);
        taskInfo.setId(Long.valueOf(id));
        return taskService.offReceiverTask(taskInfo);
    }


    /**
     * 完成任务并反馈
     * @param request
     * @param taskInfo
     * @return
     */
    @ApiOperation("完成任务并反馈")
    @PostMapping("feedbackTask")
    @ResponseBody
    public BaseResponse feedbackTask(HttpServletRequest request, TaskInfo taskInfo){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == taskInfo){
            return BaseResponse.fail("反馈数据为空");
        }
        if(StringUtils.isBlank(taskInfo.getFeedbackContent())){
            return BaseResponse.fail("反馈内容为空");
        }
        taskInfo.setFeedbackUserNo(userNo);
        return taskService.feedbackTask(taskInfo);
    }

    /**
     * 拒绝任务
     * @param request
     * @param taskInfo
     * @return
     */
    @ApiOperation("拒绝任务")
    @PostMapping("rejectTask")
    @ResponseBody
    public BaseResponse rejectTask(HttpServletRequest request, TaskInfo taskInfo){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == taskInfo){
            return BaseResponse.fail("反馈数据为空");
        }
        if(StringUtils.isBlank(taskInfo.getFeedbackContent())){
            return BaseResponse.fail("反馈内容为空");
        }
        taskInfo.setFeedbackUserNo(userNo);
        return taskService.rejectTask(taskInfo);
    }

    @ApiOperation("任务类型列表")
    @PostMapping("taskTypeList")
    public List<TaskTypeEntity> taskTypeList(){
        int num = 3;
        String[] typeNameArr = new String[]{"上报数据","日常调度任务","侦控任务"};
        List<TaskTypeEntity> typeList = new ArrayList<>();
        for(int i = 0; i<num; i++) {
            TaskTypeEntity taskType = new TaskTypeEntity();
            taskType.setTaskType(i+1);
            taskType.setTaskTypeName(typeNameArr[i]);
            typeList.add(taskType);
        }
        if(!CollectionUtils.isEmpty(typeList)){
            return typeList;
        }else {
            return new ArrayList<>();
        }
    }

    @ApiOperation("任务状态列表")
    @PostMapping("taskStateList")
    public List<TaskStateEntity> taskStateList(){
        int num = 4;
        String[] stateNameArr = new String[]{"待认领","已接受","已拒绝","已完成"};
        List<TaskStateEntity> stateList = new ArrayList<>();
        for(int i = 0; i<num; i++) {
            TaskStateEntity state = new TaskStateEntity();
            state.setState(i+1);
            state.setStateName(stateNameArr[i]);
            stateList.add(state);
        }
        if(!CollectionUtils.isEmpty(stateList)){
            return stateList;
        }else {
            return new ArrayList<>();
        }
    }

}
