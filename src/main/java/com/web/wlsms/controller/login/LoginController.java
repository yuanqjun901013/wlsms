package com.web.wlsms.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.wlsms.constants.CookieConstants;
import com.web.wlsms.entity.TokenEntity;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.service.system.TokenService;
import com.web.wlsms.service.system.UserService;
import com.web.wlsms.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("user")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    /**
     * 跳转login页面
     * @param request
     * @return
     */
    @RequestMapping("login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        String errorMessage = (String) session.getAttribute("errorMessage");
        if(StringUtils.isNotBlank(userNo)){
            request.setAttribute("userNo",userNo);
        }
        if(StringUtils.isNotBlank(errorMessage)){
            request.setAttribute("errorMessage",errorMessage);
        }
        String isLogin = (String) session.getAttribute("isLogin");
        if(StringUtils.isNotBlank(isLogin) && Boolean.valueOf(isLogin)) {//已登录
            return "views/index/index";
        }
        return "views/login/login";
    }

//    private void setResponse(HttpServletResponse response, Object object) throws IOException {
//        response.setContentType("text/plain;charset=UTF-8");
//        response.getWriter().write(JSON.toJSONString(object));
//        response.getWriter().flush();
//        response.getWriter().close();
//    }

    /**
     * 登录校验
     * @param request
     * @return
     */
    @RequestMapping("doLogin")
    public void doLogin(HttpServletRequest request, HttpServletResponse response, UserEntity user) throws IOException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", "登录失败,用户不存在");
        //查询用户信息
        UserEntity userForBase = userService.selectUserById(user.getUserNo());
        if (null == userForBase || StringUtils.isBlank(userForBase.getPwd())) {
            request.setAttribute("errorMessage", "用户不存在，请重新登录！");
            request.getSession().setAttribute("isLogin", "false");
            response.sendRedirect(request.getContextPath() + "/user/login");
        } else if(!userForBase.getPwd().equals(user.getPwd())) {
            request.setAttribute("errorMessage", "密码错误，请重新登录！");
            request.getSession().setAttribute("isLogin", "false");
            response.sendRedirect(request.getContextPath() + "/user/login");
        } else{
            String token = tokenService.getToken(userForBase);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            request.getSession().setAttribute("isLogin", "true");//如果正确，则在seesion里添加判断属性，可供拦截器判断是否登录过
            request.getSession().setAttribute("token", token);
            request.getSession().setAttribute("userNo", user.getUserNo());
            request.getSession().setAttribute("welcome", "true");//需要欢迎页
            response.addCookie(cookie);
            response.sendRedirect(request.getContextPath() + "/index/main");
            TokenEntity tokenEntity = tokenService.findByUserNo(user.getUserNo());
            if(null != tokenEntity && StringUtils.isNotBlank(tokenEntity.getUserNo())){
                tokenEntity.setToken(token);
                tokenService.updateUserToken(tokenEntity);
            }else {
                tokenEntity = new TokenEntity();
                tokenEntity.setUserNo(user.getUserNo());
                tokenEntity.setToken(token);
                tokenService.insertUserToken(tokenEntity);
            }

        }
    }

    /**
     * 注册
     * @param request
     */
    @RequestMapping("register")
    @ResponseBody
    public void register(HttpServletRequest request){
        //todo
    }

    /**
     * 注销登录
     * @param session
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        String userNo = (String) session.getAttribute("userNo");
        request.getSession().setAttribute("isLogin","false");
        String cookie = CookieUtil.getCookie(request, CookieConstants.TOKEN);
        if (StringUtils.isNotBlank(cookie)) {
            //清除cookie,设置过期时间为0
            CookieUtil.deleteCookie(response, CookieConstants.TOKEN);
        }
        session.invalidate();//使Session变成无效，及用户退出
        tokenService.deleteUserToken(userNo);
        return "views/login/login";
    }
}
