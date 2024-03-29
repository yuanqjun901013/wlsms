package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.RoleUsersRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.RoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Api(tags = {"用户角色管理"})
@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleUserController.class);

    @Autowired
    private RoleUserService roleUserService;


    /**
     * 查询用户角色信息
     *
     * @param params
     * @return
     */
    @ApiOperation("查询用户角色信息")
    @PostMapping("/queryUserRoleInfo")
    public Map<String,Object> queryRoleUserInfo(SimpleRequest params) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo queryRoleUserList =  roleUserService.queryRoleUserList(params);
            resultMap.put("total", queryRoleUserList.getTotal());
            resultMap.put("rows", queryRoleUserList.getList());
        } catch (Exception e) {
            LOGGER.error("RoleUserController&&queryUserRoleInfo is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 查询未授权的用户
     */

    /**
     * 保存用户角色
     *
     * @param params
     * @return
     */
    @ApiOperation("保存用户角色")
    @PostMapping("/saveUserRole")
    public BaseResponse saveUserRole(RoleUsersRequest params) {
        try {
            int num = roleUserService.saveUserRole(params);
            if (num > 0) {
                return BaseResponse.ok(null);
            }
            return BaseResponse.fail("添加用户角色失败！");
        } catch (Exception e) {
            LOGGER.error("RoleUserController&&saveUserRole is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 删除用户角色
     *
     * @param id
     * @return
     */
    @ApiOperation("删除用户角色")
    @PostMapping("/delUserRole")
    public BaseResponse<Map<String, Object>> delUserRole(long id) {
        try {
            int num = roleUserService.delUserRole(id);
            if (num > 0) {
                return BaseResponse.ok(null);
            }
            return BaseResponse.fail("删除用户角色失败！");
        } catch (Exception e) {
            LOGGER.error("RoleUserController&&delUserRole is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 查询角色绑定用户
     *
     * @param params
     * @return
     */
    @ApiOperation("查询角色绑定用户")
    @PostMapping("/queryUserByRoleCode")
    public BaseResponse<PageInfo> queryUserByRoleCode(@RequestBody SimpleRequest<String> params) {
        try {
            return BaseResponse.ok(roleUserService.queryUserByRoleCode(params));
        } catch (Exception e) {
            LOGGER.error("RoleUserController&&queryUserByRoleCode is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }
}
