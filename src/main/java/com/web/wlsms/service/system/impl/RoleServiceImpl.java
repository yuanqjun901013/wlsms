package com.web.wlsms.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.RoleDao;
import com.web.wlsms.dao.RoleUserDao;
import com.web.wlsms.entity.AdminRoleUserEntity;
import com.web.wlsms.request.AdminRoleRequest;
import com.web.wlsms.service.system.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleUserDao roleUserDao;
    /**
     * 查询角色列表
     * @param request
     * @return
     */
    @Override
    public PageInfo getRoleList(AdminRoleRequest request) {
        PageHelper.startPage(request.getPage(),request.getRows());
        List<AdminRoleUserEntity> list = roleDao.queryRoleList(request);
        return new PageInfo<>(list);
    }

    @Transactional
    @Override
    public int addRole(AdminRoleRequest request) {
        roleDao.deleteOneRole(request.getRoleCode());
        return roleDao.addRole(request);
    }

    /**
     * 删除角色
     * @param roleCode
     * @return
     */
    @Transactional
    @Override
    public String delRole(String roleCode) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("roleCode",roleCode);
        List<AdminRoleUserEntity> list = roleUserDao.queryRoleUserList(paramsMap);
        if(list.size() > 0){
            return "无法删除角色，该角色下存在用户角色！";
        }
        int num = roleDao.deleteOneRole(roleCode);
        // 删除角色菜单关联
        roleDao.deleteRoleAuthConfig(roleCode);
        if(num > 0){
            return "success";
        }
        return "删除角色失败！";
    }
}
