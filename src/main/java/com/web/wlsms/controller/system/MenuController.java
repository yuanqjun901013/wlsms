package com.web.wlsms.controller.system;


import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.EditMenuRequest;
import com.web.wlsms.request.MenuRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.AdminMenuResponse;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.response.MenuNodeResponse;
import com.web.wlsms.service.system.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/index/menu")
public class MenuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单配置
     *
     * @return
     */
//    @RequestMapping("/menuConfigList")
//    public BaseResponse<List<MenuNodeResponse>> menuConfigList() {
//        try {
//            return BaseResponse.ok(menuService.getAllMenuJson(null));
//        } catch (Exception e) {
//            LOGGER.error("MenuController&&menuConfigList is error", e);
//            return BaseResponse.fail("操作失败！");
//        }
//    }

    @RequestMapping("/menuConfigList")
    public List<MenuNodeResponse> menuConfigList() {
        try {
            return menuService.getAllMenuJson(null);
        } catch (Exception e) {
            LOGGER.error("MenuController&&menuConfigList is error", e);
            return new ArrayList<>();
        }
    }


    /**
     * 获取菜单信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/queryMenu")
    public BaseResponse<AdminMenuResponse> queryMenu(@RequestBody SimpleRequest<String> params) {
        try {
            return BaseResponse.ok(menuService.queryMenu(params.getRequest()));
        } catch (Exception e) {
            LOGGER.error("MenuController&&queryMenu is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 获取子菜单信息列表分页(id)
     *
     * @param
     * @return
     */
    @RequestMapping("/pageQueryMenu")
    public BaseResponse<PageInfo> pageQueryMenu(@RequestBody SimpleRequest params) {
        try {
            return BaseResponse.ok(menuService.pageQueryAdminMenu(params));
        } catch (Exception e) {
            LOGGER.error("MenuController&&menuConfigList is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 新增子菜单
     *
     * @param params
     * @return
     */
    @RequestMapping("/addMenu")
    public BaseResponse<String> addMenu(@RequestBody MenuRequest params) {
        try {
            String msg = menuService.addMenu(params);
            if ("success".equals(msg)) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail(msg);
        } catch (Exception e) {
            LOGGER.error("MenuController&&addMenu is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 修改菜单信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/editMenu")
    public BaseResponse editMenu(@RequestBody EditMenuRequest params) {
        try {
            String msg = menuService.editMenu(params);
            if ("success".equals(msg)) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail(msg);
        } catch (Exception e) {
            LOGGER.error("MenuController&&editMenu is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    /**
     * 删除菜单信息
     *
     * @param params
     * @return
     */
    @RequestMapping("/deleteMenu")
    public BaseResponse deleteMenu(@RequestBody SimpleRequest<String> params) {
        try {
            String msg = menuService.deleteMenu(params.getRequest());
            if ("success".equals(msg)) {
                return BaseResponse.ok();
            }
            return BaseResponse.fail(msg);
        } catch (Exception e) {
            LOGGER.error("MenuController&&deleteMenu is error", e);
            return BaseResponse.fail("操作失败！");
        }
    }

    @RequestMapping("/queryMenuByUserNo")
    public BaseResponse<List<MenuNodeResponse>> queryMenuByUserNo(HttpServletRequest request) {
        String userNo = request.getRemoteUser();
        try {
            return BaseResponse.ok(menuService.queryMenuByUserNo(userNo));
        } catch (Exception e) {
            LOGGER.error("MenuController&&queryMenuByUserNo is error", e);
            return BaseResponse.fail("操作失败！");
        }

    }


    /**
     * 根据用户权限获取首页默认的二级菜单及子菜单
     * @param request
     * @return
     */
    @RequestMapping("getMenuLevel")
    public List<MenuNodeResponse> getMenuLevel(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        Long parentId = Long.valueOf(request.getParameter("parentId"));
        //根据用户权限获取首页默认的二级菜单及子菜单
        List<MenuNodeResponse> queryMenusByParentId = menuService.queryMenusByParentId(userNo, parentId);
        return queryMenusByParentId;
    }


}
