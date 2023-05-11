package com.web.wlsms.controller.system;


import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.EditMenuRequest;
import com.web.wlsms.request.MenuRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.AdminMenuResponse;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.response.MenuNodeResponse;
import com.web.wlsms.service.system.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags = {"菜单管理"})
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
    @ApiOperation("菜单配置列表")
    @PostMapping("/menuConfigList")
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
    @ApiOperation("获取菜单信息")
    @PostMapping("/queryMenu")
    public BaseResponse<AdminMenuResponse> queryMenu(SimpleRequest<String> params) {
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
    @ApiOperation("获取子菜单信息列表分页")
    @PostMapping("/pageQueryMenu")
    public Map<String,Object> pageQueryMenu(SimpleRequest params) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo menuPageInfo = menuService.pageQueryAdminMenu(params);
            resultMap.put("total", menuPageInfo.getTotal());
            resultMap.put("rows", menuPageInfo.getList());
        } catch (Exception e) {
            LOGGER.error("MenuController&&pageQueryMenu is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }

    /**
     * 新增子菜单
     *
     * @param params
     * @return
     */
    @ApiOperation("新增子菜单")
    @PostMapping("/addMenu")
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
    @ApiOperation("修改菜单信息")
    @PostMapping("/editMenu")
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
    @ApiOperation("删除菜单信息")
    @PostMapping("/deleteMenu")
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

    @ApiOperation("根据账号查询菜单")
    @PostMapping("/queryMenuByUserNo")
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
    @ApiOperation("根据用户权限获取首页默认的二级菜单及子菜单")
    @PostMapping("getMenuLevel")
    public List<MenuNodeResponse> getMenuLevel(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        Long parentId = Long.valueOf(request.getParameter("parentId"));
        //根据用户权限获取首页默认的二级菜单及子菜单
        List<MenuNodeResponse> queryMenusByParentId = menuService.queryMenusByParentId(userNo, parentId);
        return queryMenusByParentId;
    }


}
