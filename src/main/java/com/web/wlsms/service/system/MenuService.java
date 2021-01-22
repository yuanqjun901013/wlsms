package com.web.wlsms.service.system;


import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.EditMenuRequest;
import com.web.wlsms.request.MenuRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.AdminMenuResponse;
import com.web.wlsms.response.MenuNodeResponse;

import java.util.List;

public interface MenuService {

    /**
     * 获取所有菜单
     *
     * @param roleCode
     * @return
     */
    List<MenuNodeResponse> getAllMenuJson(String roleCode);

    /**
     * 获取菜单信息
     *
     * @param menuId
     * @return
     */
    AdminMenuResponse queryMenu(String menuId);

    /**
     * 获取子菜单信息列表分页
     *
     * @param request
     * @return
     */
    PageInfo pageQueryAdminMenu(SimpleRequest<Integer> request);

    /**
     * 新增子菜单
     *
     * @param request
     * @return
     */
    String addMenu(MenuRequest request);

    /**
     * 修改子菜单
     *
     * @param request
     * @return
     */
    String editMenu(EditMenuRequest request);

    /**
     * 删除子菜单
     *
     * @param id
     * @return
     */
    String deleteMenu(String id);

    /**
     * 根据登录用户获取菜单信息
     *
     * @param userNo
     * @return
     */
    List<MenuNodeResponse> queryMenuByUserNo(String userNo);

    /**
     * 查询父类菜单
     * @return
     */
    List<MenuNodeResponse> queryMenusParent();

    List<MenuNodeResponse> queryMenusByParentId(String userNo, Long parentId);
}
