package com.web.wlsms.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MenuDao;
import com.web.wlsms.dao.RoleDao;
import com.web.wlsms.entity.AdminMenuEntity;
import com.web.wlsms.entity.AdminRoleAuthEntity;
import com.web.wlsms.request.EditMenuRequest;
import com.web.wlsms.request.MenuRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.AdminMenuResponse;
import com.web.wlsms.response.MenuNodeResponse;
import com.web.wlsms.service.system.MenuService;
import com.web.wlsms.service.system.RoleUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private RoleUserService roleUserService;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleDao roleDao;

    /**
     * 获取所有菜单
     *
     * @param roleCode
     * @return slave
     */
    @Override
    public List<MenuNodeResponse> getAllMenuJson(String roleCode) {
        List<MenuNodeResponse> menuNodes = getMenuNodeListAll();
        //角色编码不为空，获取角色权限
        if (StringUtils.isNotEmpty(roleCode)) {
            Map param = new HashMap();
            param.put("roleCode", roleCode);
            List<AdminRoleAuthEntity> allAuths = roleDao.queryAllMenuAuths(param);
            Map<Long, ?> authMap = authListToMap(allAuths).get(roleCode);
            if (null != authMap && authMap.size() > 0) {
                for (MenuNodeResponse menu : menuNodes) {
                    getMenuChecked(menu,authMap);
                }
            }
        }
//        //菜单节点
        MenuNodeResponse root = new MenuNodeResponse();
        root.setId(0L);
        root.setText("菜单目录");
        root.setChildren(menuNodes);
        root.setState("open");
        List rootList = new ArrayList();
        rootList.add(root);
        return rootList;
    }


    /**
     * 列出所有菜单，用于权限配置
     *
     * @return
     */
    public List<MenuNodeResponse> getMenuNodeListAll() {
        List<AdminMenuEntity> allMenus = menuDao.queryAllMenus();
        List<AdminMenuEntity> rootList = new ArrayList();
        List<AdminMenuEntity> subList = new ArrayList();
        for (AdminMenuEntity menu : allMenus) {
            if (menu.getParentId() == 0) {
                rootList.add(menu);
            } else {
                subList.add(menu);
            }
        }
        return getMenuNodesAll(rootList, subList);
    }

    private List<MenuNodeResponse> getMenuNodesAll(List<AdminMenuEntity> rootList, List<AdminMenuEntity> subList) {
        Map<Long, List<AdminMenuEntity>> menuMap = new TreeMap();
        for (AdminMenuEntity menu : subList) {
            Long parentId = menu.getParentId();
            List<AdminMenuEntity> menuSubList = menuMap.get(parentId);
            if (null == menuSubList) {
                menuSubList = new ArrayList<AdminMenuEntity>();
            }
            menuSubList.add(menu);
            menuMap.put(parentId, menuSubList);
        }
        List<MenuNodeResponse> nodeList = new ArrayList();
        for (AdminMenuEntity menu : rootList) {
            MenuNodeResponse node = menuRecurveToNode(menu, menuMap);
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 获取所有菜单节点
     *
     * @param rootList
     * @param subList
     * @return
     */
    private List<MenuNodeResponse> getMenuNodes(List<AdminMenuEntity> rootList, List<AdminMenuEntity> subList) {
        Map<Long, List<AdminMenuEntity>> menuMap = new TreeMap();
        for (AdminMenuEntity menu : subList) {
            Long parentId = menu.getParentId();
            List<AdminMenuEntity> menuSubList = menuMap.get(parentId);
            if (null == menuSubList) {
                menuSubList = new ArrayList<AdminMenuEntity>();
            }
            menuSubList.add(menu);
            menuMap.put(parentId, menuSubList);
        }
        List<MenuNodeResponse> nodeList = new ArrayList();
        for (AdminMenuEntity menu : rootList) {
            MenuNodeResponse node = menuRecurveToNode(menu, menuMap);
            if(menu.getParentId() == 0){
                node.setIconCls(menu.getIconCls());
            }
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 获取菜单子节点
     *
     * @param menu
     * @param allSubMenuMap
     * @return
     */
    private MenuNodeResponse menuRecurveToNode(AdminMenuEntity menu, Map<Long, List<AdminMenuEntity>> allSubMenuMap) {
        MenuNodeResponse node = new MenuNodeResponse();
        node.setId(menu.getId());
        node.setText(menu.getName());
        node.setChecked(menu.isChecked());
        node.setMenu(menu.getMenuCode());
        node.setLevel(menu.getLevel());
        Long parentId = menu.getId();
        if (allSubMenuMap.get(parentId) != null) { //取得子节点
            List<MenuNodeResponse> children = new ArrayList();
            List<AdminMenuEntity> subMenus = allSubMenuMap.get(parentId);
            for (AdminMenuEntity subMenu : subMenus) {
                MenuNodeResponse subMenuNode = menuRecurveToNode(subMenu, allSubMenuMap);
                children.add(subMenuNode);
            }
            node.setState("closed");
            node.setChildren(children);
        } else {
            node.setUrl(menu.getUrl());
        }
        return node;
    }

    /**
     * 获取角色权限
     *
     * @param authList
     * @return
     */
    private Map<String, Map<Long, Long>> authListToMap(List<AdminRoleAuthEntity> authList) {
        Map<String, Map<Long, Long>> authMaps = new HashMap();
        for (AdminRoleAuthEntity auth : authList) {
            Map<Long, Long> map = authMaps.get(auth.getRoleCode());
            if (null == map) {
                map = new HashMap<Long, Long>();
            }
            map.put(auth.getMenuId(), null);
            authMaps.put(auth.getRoleCode(), map);
        }
        return authMaps;
    }

    /**
     * 获取菜单信息
     *
     * @param menuId
     * @return
     */
    @Override
    public AdminMenuResponse queryMenu(String menuId) {
        AdminMenuEntity adminMenu = menuDao.queryMenuConfig(menuId);
        AdminMenuEntity menuConfig = null;
        if (null == adminMenu) {
            menuConfig = new AdminMenuEntity();
            menuConfig.setId(0L);
            menuConfig.setSysCode("admin");
            menuConfig.setName("根目录");
            menuConfig.setUrl("/");
        } else {
            menuConfig = new AdminMenuEntity();
            menuConfig.setId(adminMenu.getId());
            menuConfig.setSysCode(adminMenu.getSysCode());
            menuConfig.setName(adminMenu.getName());
            menuConfig.setUrl(adminMenu.getUrl());
            menuConfig.setParentId(adminMenu.getParentId());
            menuConfig.setMenuCode(adminMenu.getMenuCode());
        }
        AdminMenuResponse adminMenuResponse = new AdminMenuResponse();
        BeanUtils.copyProperties(menuConfig, adminMenuResponse);
        return adminMenuResponse;
    }

    /**
     * 获取子菜单列表分页
     *
     * @param request
     * @return
     */
    @Override
    public PageInfo pageQueryAdminMenu(SimpleRequest<Integer> request) {
        PageHelper.startPage(request.getPage(), request.getRows());
        Map map = new HashMap();
        map.put("parentId",request.getRequest());
        List<AdminMenuEntity> list = menuDao.queryChildrenMenu(map);
        return new PageInfo<>(list);
    }

    /**
     * 添加菜单
     *
     * @param
     * @return
     */
    @Transactional
    @Override
    public String addMenu(MenuRequest request) {
        String msg = "";
        AdminMenuEntity adminMenu = menuDao.queryMenuConfig(request.getId());
        if (null != adminMenu) {
            if (null != adminMenu.getUrl() && !adminMenu.getUrl().isEmpty()) {
                msg = "添加失败！该菜单项为子节点不能添加下级！";
            } else {
                int num = menuDao.addMenu(request);
                if (num > 0) {
                    return msg = "success";
                }
            }
        } else {
            if ("根目录".equals(request.getParentName())) {
                int num = menuDao.addMenu(request);
                if (num > 0) {
                    return msg = "success";
                }
            }
            msg = "添加失败！请重试！";
        }
        return msg;
    }

    /**
     * 修改菜单项
     *
     * @param paramsMap
     * @return
     */
    @Transactional
    @Override
    public String editMenu(EditMenuRequest paramsMap) {
        String msg = "";
        int num = menuDao.editMenu(paramsMap);
        if (num > 0) {
            return msg = "success";
        }
        msg = "修改失败！请重试！";
        return msg;
    }

    /**
     * 删除菜单项
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public String deleteMenu(String id) {
        String msg = "";
        int parentCount = menuDao.menuConfigCount(id);
        if (parentCount > 0) {
            int childCount = menuDao.queryMenuCount(id);
            if (childCount > 0) {
                msg = "删除失败！该菜单项为父级菜单不能删除！";
            } else {
                menuDao.deleteMenu(id);
                return "success";
            }
        } else {
            msg = "删除失败！菜单信息不存在，请重试！";
        }
        return msg;
    }

    /**
     * 用户工号过滤菜单
     *
     * @param userNo
     * @return
     */
    @Override
    public List<MenuNodeResponse> queryMenuByUserNo(String userNo) {
        String roleCode = roleUserService.queryUserRoleCode(userNo);
        List<MenuNodeResponse> menuNodes = getMenuNodeListFiltered(roleCode);
        return menuNodes;
    }




    //需要过滤掉没有权限的菜单
    private List<MenuNodeResponse> getMenuNodeListFiltered(String roleCode) {
        Map param = new HashMap();
        param.put("roleCode", roleCode);
        List<AdminMenuEntity> allMenus = menuDao.queryAuthedMenus(param);
        List<AdminMenuEntity> rootList = new ArrayList();
        List<AdminMenuEntity> subList = new ArrayList();
        for (AdminMenuEntity menu : allMenus) {
            if (menu.getParentId() == 0) {
                rootList.add(menu);
            } else {
                subList.add(menu);
            }
        }
        return getMenuNodes(rootList, subList);
    }

    // 有权限的子菜单添加checked:true
    private void getMenuChecked(MenuNodeResponse menus, Map<Long, ?> authMap) {
        List<MenuNodeResponse> list = menus.getChildren();
        if(CollectionUtils.isNotEmpty(list)){
            for(MenuNodeResponse menu : list){
                menu.setChecked(authMap.containsKey(menu.getId()));
            }
        }else{
            menus.setChecked(authMap.containsKey(menus.getId()));
        }
    }

    //查询父类菜单
    public List<MenuNodeResponse> queryMenusParent() {
        List<AdminMenuEntity> allMenus = menuDao.queryMenusParent();
        List<AdminMenuEntity> rootList = new ArrayList();
        List<AdminMenuEntity> subList = new ArrayList();
        for (AdminMenuEntity menu : allMenus) {
            if (menu.getParentId() == 0) {
                rootList.add(menu);
            } else {
                subList.add(menu);
            }
        }
        return getMenuNodes(rootList, subList);
    }

    //指定查询二级以下菜单
    public List<MenuNodeResponse> queryMenusByParentId(String userNo, Long parentId) {
        String roleCode = roleUserService.queryUserRoleCode(userNo);
        Map param = new HashMap();
        param.put("roleCode", roleCode);
//        param.put("level", level);
//        param.put("parentId", parentId);
//        List<AdminMenuEntity> allMenus = menuDao.queryMenusLevel(param);
        List<MenuNodeResponse> menuNodesAllByRoleCode = getMenuNodeListFiltered(roleCode);
        if(null == menuNodesAllByRoleCode || menuNodesAllByRoleCode.size() == 0){
            return new ArrayList<>();
        }
        List<MenuNodeResponse> loadMenuByParentId = new ArrayList<>();
        for(MenuNodeResponse menuNodeResponse:menuNodesAllByRoleCode){
            if(menuNodeResponse.getId() == parentId && null != menuNodeResponse.getChildren()) {
                loadMenuByParentId.addAll(menuNodeResponse.getChildren());
            }
        }
        return loadMenuByParentId;
    }

}
