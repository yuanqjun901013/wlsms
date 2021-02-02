package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.RoleUsersRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping("/queryUserRoleInfo")
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
     * 保存用户角色
     *
     * @param params
     * @return
     */
    @RequestMapping("/saveUserRole")
    public BaseResponse saveUserRole(@RequestBody RoleUsersRequest params) {
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
     * @param params(userNo)
     * @return
     */
    @RequestMapping("/delUserRole")
    public BaseResponse<Map<String, Object>> delUserRole(@RequestBody SimpleRequest<String> params) {
        try {
            int num = roleUserService.delUserRole(params.getRequest());
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
    @RequestMapping("/queryUserByRoleCode")
    public BaseResponse<PageInfo> queryUserByRoleCode(@RequestBody SimpleRequest<String> params) {
        try {
            return BaseResponse.ok(roleUserService.queryUserByRoleCode(params));
        } catch (Exception e) {
            LOGGER.error("RoleUserController&&queryUserByRoleCode is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }
}
