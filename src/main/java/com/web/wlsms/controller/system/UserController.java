package com.web.wlsms.controller.system;

import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.service.system.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("editUserByUserNo")
    public int editUserByUserNo(HttpServletRequest request, UserEntity userEntity){
        return userService.editUserByUserNo(userEntity);
    }

    @RequestMapping("editUserPwd")
    public int editUserPwd(HttpServletRequest request, UserEntity userEntity){
        return userService.editUserPwd(userEntity);
    }

}
