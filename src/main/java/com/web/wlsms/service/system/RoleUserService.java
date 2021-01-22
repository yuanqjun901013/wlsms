package com.web.wlsms.service.system;

import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.RoleUsersRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.RoleUserResponse;


public interface RoleUserService {


    /**
     * 保存用户角色
     * @param request
     * @return
     */
    int saveUserRole(RoleUsersRequest request);

    /**
     * 删除用户角色
     * @param userNo
     * @return
     */
    int delUserRole(String userNo);

    String queryUserRoleCode(String userNo);

    RoleUserResponse queryRoleUserByCode(String roleCode);

    /**
     *   角色编码查询绑定用户
     * @param request
     * @return
     */
    PageInfo queryUserByRoleCode(SimpleRequest<String> request);

    PageInfo queryRoleUserList(SimpleRequest<String> request);
}
