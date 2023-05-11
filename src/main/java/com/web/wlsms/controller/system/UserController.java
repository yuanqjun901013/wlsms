package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.UserEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.system.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/admin/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @ApiOperation("编辑用户信息")
    @PostMapping("editUserByUserNo")
    public int editUserByUserNo(HttpServletRequest request, UserEntity userEntity){
        return userService.editUserByUserNo(userEntity);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("editUserPwd")
    public int editUserPwd(HttpServletRequest request, UserEntity userEntity){
        return userService.editUserPwd(userEntity);
    }

    /**
     * 查询用户列表
     * @param params
     * @return
     */
    @ApiOperation("查询用户列表")
    @PostMapping("getUserList")
    public Map<String,Object> getUserList(SimpleRequest params){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getUserList = userService.getUserList(params);
            resultMap.put("total", getUserList.getTotal());
            resultMap.put("rows", getUserList.getList());
        }catch (Exception e){
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    //供下拉框选择
    @ApiOperation("供下拉框选择")
    @PostMapping("getUserArr")
    public Map<String,Object> getUserArr(){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            List<UserEntity> getUserList = userService.getUserArr();
            resultMap.put("rows", getUserList);
        }catch (Exception e){
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    @ApiOperation("添加用户")
    @PostMapping("saveUser")
    public int saveUser(UserEntity userEntity){
        if(null == userEntity){//信息不能为空
            return 2;
        }
        if(StringUtils.isBlank(userEntity.getUserNo())){
            return 3;
        }
        if(StringUtils.isBlank(userEntity.getUserName())){
            return 4;
        }
        try {
            return userService.saveUser(userEntity);
        }catch (Exception e){
            return 0;
        }

    }

    @ApiOperation("删除用户")
    @PostMapping("deleteUser")
    public int deleteUser(UserEntity userEntity){
        if(null == userEntity){//信息不能为空
            return 2;
        }
        try {
            return userService.deleteUser(userEntity);
        }catch (Exception e){
            return 0;
        }

    }
}
