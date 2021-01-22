package com.web.wlsms.configura;

import com.web.wlsms.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

//    /**
//     * 拦截器
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        //添加对用户是否登录的拦截器，并添加过滤项、排除项
//        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/**/*.js","/**/*.css","/**/images/**","/**/themes/**")//排除样式、脚本、图片等资源文件
//                .excludePathPatterns("/user/login")//排除登录页面
////                .excludePathPatterns("/user/login")//排除验证码
//                .excludePathPatterns("/user/doLogin");//排除用户点击登录按钮
//    }

//    /**
//     * 跳转默认方法
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/").setViewName("forward:/demo/accordion/horizontal.html");//水平折叠菜单
////        registry.addViewController("/").setViewName("forward:/demo/accordion/multiple.html");//多面折叠菜单
////        registry.addViewController("/").setViewName("forward:/demo/accordion/tools.html");//手风琴工具条折叠菜单
////        registry.addViewController("/").setViewName("forward:/demo/accordion/fluid.html");//流体折叠面板
////        registry.addViewController("/").setViewName("forward:/demo/accordion/expandable.html");//保持展开折叠
//        registry.addViewController("/").setViewName("forward:/index/main");//默认首页
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")// 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
                .excludePathPatterns("/**/*.js","/**/*.css","/**/images/**","/**/themes/**")//排除样式、脚本、图片等资源文件
                .excludePathPatterns("/user/login")//排除验证码
                .excludePathPatterns("/admin/menu/**")//开发测试免登录接口
                .excludePathPatterns("/admin/menuRole/**")//开发测试免登录接口
                .excludePathPatterns("/user/doLogin");//排除用户点击登录按钮
    }



    /**
     * 跳转默认方法
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("forward:/index/main");//默认首页
        registry.addViewController("/").setViewName("redirect:/index/main");//默认首页
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void addCorsMappings(CorsRegistry arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void addFormatters(FormatterRegistry arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configurePathMatch(PathMatchConfigurer arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Validator getValidator() {
        // TODO Auto-generated method stub
        return null;
    }
}