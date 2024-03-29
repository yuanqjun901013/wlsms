package com.web.wlsms.controller.index;


import com.alibaba.fastjson.JSONObject;
import com.web.wlsms.entity.AdminMenuEntity;
import com.web.wlsms.entity.AdminRoleUserEntity;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.response.MenuNodeResponse;
import com.web.wlsms.service.system.MenuService;
import com.web.wlsms.service.system.RoleUserService;
import com.web.wlsms.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"首页"})
@Controller
@RequestMapping("index")
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Resource
    private RoleUserService roleUserService;


    /**
     * 首页
     * @param request
     * @return
     */
    @ApiOperation("首页")
    @GetMapping("main")
    public String main(HttpServletRequest request) {
        commonSession(request);
        return "views/index/index";
    }


    /**
     * 首页中间默认展示内容
     * @param request
     * @return
     */
    @ApiOperation("首页中间默认展示内容")
    @GetMapping("middlePage")
    public String middlePage(HttpServletRequest request) {
        request.setAttribute("tab","告警");
        return "views/middle/middle";
    }

    @ApiOperation("消息展示")
    @GetMapping("messagePage")
    public String messagePage(HttpServletRequest request) {
        return "views/message/message";
    }

    /**
     * 重定向页面请求
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ApiOperation("重定向页面请求")
    @GetMapping("forwardToPage")
    public String forwardToPage(HttpServletRequest request){
        LOGGER.info("forwardToPage");
        String url = request.getParameter("url");
        String text = request.getParameter("text");
        String menu = request.getParameter("menu");
        request.setAttribute("tab", text);
        request.setAttribute("menu", menu);
        commonSession(request);//默认加载首页用户信息
        return url;
    }

    /**
     * 加载个人资料Modal弹窗页
     * @param request
     * @return
     */
    @ApiOperation("加载个人资料Modal弹窗页")
    @GetMapping("userModalPage")
    public String userModalPage(HttpServletRequest request) {
        commonSession(request);
        return "views/index/userModalPage";
    }

    @ApiOperation("待认领任务")
    @GetMapping("waitingTask")
    public String waitingTask(HttpServletRequest request) {
        commonSession(request);
        return "views/task/waitingTask";
    }

    @ApiOperation("待处理任务")
    @GetMapping("todoTask")
    public String todoTask(HttpServletRequest request) {
        commonSession(request);
        return "views/task/todoTask";
    }



    /**
     * 加载修改密码Modal弹窗页
     * @param request
     * @return
     */
    @ApiOperation("加载修改密码Modal弹窗页")
    @GetMapping("pwdModalPage")
    public String pwdModalPage(HttpServletRequest request) {
        commonSession(request);
        return "views/index/pwdModalPage";
    }

    /**
     * 公共session
     * @param request
     */
    private void commonSession(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        String welcome = (String)session.getAttribute("welcome");
        if(null != welcome && StringUtils.isNotBlank(welcome) && Boolean.valueOf(welcome)) {//
            request.setAttribute("welcomeValue","1");//欢迎页需要
        }else{
            request.setAttribute("welcomeValue","0");//欢迎页不需要
        }
        UserEntity user = userService.selectUserById(userNo);
        request.setAttribute("userInfo",user);//用户信息
        AdminRoleUserEntity userRole = roleUserService.queryUserRole(userNo);
        request.setAttribute("userNameCode", user.getUserName()+"("+userNo+")");//姓名工号
        if(null != userRole && StringUtils.isNotBlank(userRole.getRoleName())){
            request.setAttribute("userNameCode", user.getUserName()+"("+userRole.getRoleName()+")");//用户角色
        }
        //根据用户获取权限下所有菜单树
//        List<MenuNodeResponse> getAllMenuJson =  menuService.queryMenuByUserNo(userNo);
//        request.setAttribute("menuList", getAllMenuJson);//权限菜单
        //默认加载一级菜单用于加载显示顶部栏
        List<MenuNodeResponse> queryMenusParent = menuService.queryMenusParent();
        request.setAttribute("menusParentList", queryMenusParent);//父类菜单
        request.setAttribute("main","主页");//主题栏
        //根据用户权限获取首页默认的二级菜单及子菜单
        List<MenuNodeResponse> queryMenusByParentId = menuService.queryMenusByParentId(userNo, 1L);
        request.setAttribute("menuLevel", queryMenusByParentId);//默认加载二级菜单
        request.getSession().setAttribute("welcome", "false");//欢迎页面置为否
    }
}
