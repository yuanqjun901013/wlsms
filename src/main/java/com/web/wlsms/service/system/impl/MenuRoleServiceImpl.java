package com.web.wlsms.service.system.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.MenuDao;
import com.web.wlsms.dao.RoleDao;
import com.web.wlsms.entity.AdminMenuEntity;
import com.web.wlsms.entity.AdminRoleAuthEntity;
import com.web.wlsms.entity.AdminRoleUserEntity;
import com.web.wlsms.request.SaveRoleRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.service.system.MenuRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuRoleServiceImpl implements MenuRoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private MenuDao menuDao;

    /**
     * 保存角色权限配置
     * @param request
     * @return
     */
    @Override
    public int saveRoleAuthConfig(SaveRoleRequest request) {
        Map<Long, AdminMenuEntity> menusMap = menuListToMap(getAllAdminMenuList());
        Set<Long> set = new HashSet();
        if(StringUtils.isNotEmpty(request.getMenuIds())){
            String[] menuIdArr = request.getMenuIds().split(",");
            for (String menuId : menuIdArr) {
                seekParentMenuId(Long.valueOf(menuId), menusMap, set);
            }
        }
        List<AdminRoleAuthEntity> params = new ArrayList<>();
        for(Iterator<Long> it = set.iterator(); it.hasNext();) {
            AdminRoleAuthEntity roleAuth = new AdminRoleAuthEntity();
            roleAuth.setRoleCode(request.getRoleCode());
            roleAuth.setMenuId(it.next());
            params.add(roleAuth);
        }
        roleDao.deleteRoleAuthConfig(request.getRoleCode());
        int num = 0;
        if(CollectionUtils.isNotEmpty(params)){
           num  =  roleDao.insertRoleAuthConfig(params);
        }else {
            //权限全部取消时
            num = 1;
        }
        return num;
    }

    private Map<Long, AdminMenuEntity> menuListToMap(List<AdminMenuEntity> list) {
        Map<Long, AdminMenuEntity> map = new HashMap(list.size());
        for (AdminMenuEntity menu : list) {
            map.put(menu.getId(), menu);
        }
        return map;
    }

    private List<AdminMenuEntity> getAllAdminMenuList() {
        return menuDao.queryAllMenus();
    }

    /**
     * 找出每一个节点的父节点
     * @param menuId
     * @param menusMap
     * @param set
     */
    private void seekParentMenuId(Long menuId, Map<Long, AdminMenuEntity> menusMap, Set<Long> set) {
        if(null != menusMap.get(menuId)) {  //根节点不存储在数据库，所以没有
            set.add(menuId);
            Long parentId = menusMap.get(menuId).getParentId();
            if(parentId > 0) {
                seekParentMenuId(parentId,menusMap,set);
            }
        }
    }

    @Override
    public PageInfo getAuthMenuRole(SimpleRequest request){
        PageHelper.startPage(request.getPage(),request.getRows());
        List<AdminRoleAuthEntity> list = roleDao.getAuthMenuRole();
        return new PageInfo<>(list);
    }
}
