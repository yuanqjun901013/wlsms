package com.web.wlsms.controller.task;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping("/getMyPulseTask")
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
    @RequestMapping("/getWaitingTask")
    public Map<String,Object> getWaitingTask(SimpleRequest params, HttpServletRequest request) {
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
    @RequestMapping("/getTodoTask")
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
    @RequestMapping("/getEndTask")
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
    @RequestMapping("/getAllTask")
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
}
