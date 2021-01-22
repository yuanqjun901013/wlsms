package com.web.wlsms.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.web.wlsms.annotation.PassToken;
import com.web.wlsms.annotation.UserLoginToken;
import com.web.wlsms.constants.CookieConstants;
import com.web.wlsms.dao.TokenDao;
import com.web.wlsms.entity.TokenEntity;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.service.system.UserService;
import com.web.wlsms.utils.CookieUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Resource
    protected TokenDao tokenDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)throws Exception {
        if (isLogin(request)) {
            return true;
        }else {
            //未登录
            //直接重定向到登录页面
            HttpSession session = request.getSession(true);
            String userNo = (String) session.getAttribute("userNo");
            request.getSession().setAttribute("isLogin","false");
            String cookie = CookieUtil.getCookie(request, CookieConstants.TOKEN);
            if (StringUtils.isNotBlank(cookie)) {
                //清除cookie,设置过期时间为0
                CookieUtil.deleteCookie(response, CookieConstants.TOKEN);
            }
            session.invalidate();//使Session变成无效，及用户退出
            tokenDao.deleteUserToken(userNo);
            response.sendRedirect(request.getContextPath()+"/user/login");
            return false;
        }

    }
//
    /**
     * 检查是否登录
     *
     * @param request
     * @return
     */
    private boolean isLogin(HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(true);
        String isLogin = (String)session.getAttribute("isLogin");
        if(null != isLogin && Boolean.valueOf(isLogin)){//已登录
            String userNo = (String) session.getAttribute("userNo");
            String tokenSession = (String) session.getAttribute("token");
            if(StringUtils.isBlank(userNo)){//没有用户信息
                return false;
            }
            //判断Token过期
            TokenEntity tokenEntity= tokenDao.findByUserNo(userNo);
            if(null == tokenEntity || StringUtils.isBlank(tokenEntity.getToken())){//token不存在
                return false;
            }
            if(!tokenEntity.getToken().equals(tokenSession)){
                return false;
            }
            String buildTime = tokenEntity.getBuildTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date tokenTime = formatter.parse(buildTime);
            int overTime=(int)(new Date().getTime()-tokenTime.getTime())/1000;
            if(overTime > 60*60){
                return false;
            }
            return true;
        }
        return false;
    }





//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
//        String token = request.getHeader("token");// 从 http 请求头中取出 token
//        // 如果不是映射到方法直接通过
//         if(!(object instanceof HandlerMethod)){
//            return true;
//        }
//        HandlerMethod handlerMethod=(HandlerMethod)object;
//        Method method=handlerMethod.getMethod();
//        //检查是否有passToken注释，有则跳过认证
//        if (method.isAnnotationPresent(PassToken.class)) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
//        //检查有没有需要用户权限的注解
//        if (method.isAnnotationPresent(UserLoginToken.class)) {
//            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//            if (userLoginToken.required()) {
//                // 执行认证
//                if (token == null) {
//                    throw new RuntimeException("无token，请重新登录");
//                }
//                // 获取 token 中的 user id
//                String userId;
//                try {
//                    userId = JWT.decode(token).getAudience().get(0);
//                } catch (JWTDecodeException j) {
//                    throw new RuntimeException("401");
//                }
//                UserEntity user = userService.selectUserById(userId);
//                if (user == null) {
//                    throw new RuntimeException("用户不存在，请重新登录");
//                }
//                // 验证 token
//                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPwd())).build();
//                try {
//                    jwtVerifier.verify(token);
//                } catch (JWTVerificationException e) {
//                    throw new RuntimeException("401");
//                }
//                return true;
//            }
//        }
//        return true;
//    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
//        //此处为不需要登录的接口放行
//        if (request.getRequestURI().contains("/user/login") || request.getRequestURI().contains("/user/doLogin") || request.getRequestURI().contains("/user/register")) {
//            return true;
//        }
//        //权限路径拦截
//        //PrintWriter resultWriter = arg1.getOutputStream();
//        // TODO: 有时候用PrintWriter 回报 getWriter() has already been called for this response
//        //换成ServletOutputStream就OK了
//        response.setContentType("text/html;charset=utf-8");
//        ServletOutputStream resultWriter = response.getOutputStream();
//        final String headerToken=response.getHeader("token");
//        //判断请求信息
//        if(null==headerToken||headerToken.trim().equals("")){
//            resultWriter.write("你没有token,需要登录".getBytes());
//            resultWriter.flush();
//            resultWriter.close();
//            return false;
//        }
//        //解析Token信息
//        try {
//            Claims claims = Jwts.parser().setSigningKey("preRead").parseClaimsJws(headerToken).getBody();
//            String userNo=(String)claims.get("userNo");
//            //根据客户Token查找数据库Token
//            TokenEntity myToken= tokenDao.findByUserId(userNo);
//
//            //数据库没有Token记录
//            if(null==myToken) {
//                resultWriter.write("我没有你的token？,需要登录".getBytes());
//                resultWriter.flush();
//                resultWriter.close();
//                return false;
//            }
//            //数据库Token与客户Token比较
//            if( !headerToken.equals(myToken.getToken()) ){
//                resultWriter.print("你的token修改过？,需要登录");
//                resultWriter.flush();
//                resultWriter.close();
//                return false;
//            }
//            //判断Token过期
//            Date tokenDate= claims.getExpiration();
//            int overTime=(int)(new Date().getTime()-tokenDate.getTime())/1000;
//            if(overTime>60*60*24*3){
//                resultWriter.write("你的token过期了？,需要登录".getBytes());
//                resultWriter.flush();
//                resultWriter.close();
//                return false;
//            }
//
//        } catch (Exception e) {
//            resultWriter.write("反正token不对,需要登录".getBytes());
//            resultWriter.flush();
//            resultWriter.close();
//            return false;
//        }
//        //最后才放行
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
