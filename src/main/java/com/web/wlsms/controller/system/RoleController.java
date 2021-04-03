package com.web.wlsms.controller.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.AdminRoleRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/role")
public class RoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/queryRole")
    public Map<String,Object> queryRole(SimpleRequest params) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getRoleList =   roleService.getRoleList(params);
            resultMap.put("total", getRoleList.getTotal());
            resultMap.put("rows", getRoleList.getList());
        } catch (Exception e) {
            LOGGER.error("RoleController&&queryRole is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }


    /**
     * 添加角色
     *
     * @param params
     * @return
     */
    @RequestMapping("/addRole")
    public BaseResponse addRole(@RequestBody AdminRoleRequest params) {
        try {
            int num = roleService.addRole(params);
            //todo
            //绑定菜单
            if (num > 0) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail("添加角色失败！");
        } catch (Exception e) {
            LOGGER.error("RoleController&&addRole is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 删除角色
     *
     * @param params
     * @return
     */
    @RequestMapping("/delRole")
    public BaseResponse delRole(@RequestBody SimpleRequest<String> params) {
        try {
            String str = roleService.delRole(params.getRequest());
            if ("success".equals(str)) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail(str);
        } catch (Exception e) {
            LOGGER.error("RoleController&&delRole is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

}
