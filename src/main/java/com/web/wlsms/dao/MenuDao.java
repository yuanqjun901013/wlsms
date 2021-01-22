package com.web.wlsms.dao;

import com.web.wlsms.entity.AdminMenuEntity;
import com.web.wlsms.request.EditMenuRequest;
import com.web.wlsms.request.MenuRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuDao {

    List<AdminMenuEntity> queryAllMenus();

    AdminMenuEntity queryMenuConfig(String id);

    List<AdminMenuEntity> queryChildrenMenu(Integer parentId);

    int addMenu(MenuRequest request);

    int queryMenuCount(String parentId);

    int editMenu(EditMenuRequest request);

    int menuConfigCount(String id);

    int deleteMenu(String id);

    List<AdminMenuEntity> queryAuthedMenus(Map<String, Object> map);

    List<AdminMenuEntity> queryMenusParent();

    List<AdminMenuEntity> queryMenusLevel(Map<String, Object> map);
}
