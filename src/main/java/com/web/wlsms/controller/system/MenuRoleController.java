package com.web.wlsms.controller.system;


import com.web.wlsms.request.SaveRoleRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.response.MenuNodeResponse;
import com.web.wlsms.service.system.MenuRoleService;
import com.web.wlsms.service.system.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/menuRole")
public class MenuRoleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuRoleController.class);

    @Autowired
    private MenuRoleService menuRoleService;
    @Autowired
    private MenuService menuService;

    /**
     * 获取角色权限
     *
     * @param params
     * @return
     */
    @RequestMapping("/authMenu")
    public BaseResponse<List<MenuNodeResponse>> authMenu(@RequestBody SimpleRequest<String> params) {
        try {
            return BaseResponse.ok(menuService.getAllMenuJson(params.getRequest()));
        } catch (Exception e) {
            LOGGER.error("MenuRoleController&&authMenu is error", e);
            return BaseResponse.fail("查询失败！");
        }
    }

    /**
     * 保存更新权限配置
     *
     * @param param
     * @return
     */
    @RequestMapping("/saveRoleAuthConfig")
    public BaseResponse<String> saveRoleAuthConfig(@RequestBody SaveRoleRequest param) {

        try {
            int count = menuRoleService.saveRoleAuthConfig(param);
            if (count > 0) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail("授权失败！");
        } catch (Exception e) {
            LOGGER.error("MenuRoleController&&saveRoleAuthConfig is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

}
