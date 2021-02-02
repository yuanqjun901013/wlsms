package com.web.wlsms.service.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.AdminRoleRequest;
import com.web.wlsms.request.SimpleRequest;

public interface RoleService {

    /**
     * 查询角色列表
     *
     * @param request
     * @return
     */
//    PageInfo getRoleList(AdminRoleRequest request);
    PageInfo getRoleList(SimpleRequest request);

    /**
     * 添加角色
     *
     * @param request
     * @return
     */
    int addRole(AdminRoleRequest request);

    /**
     * 删除角色
     *
     * @param roleCode
     * @return
     */
    String delRole(String roleCode);
}
