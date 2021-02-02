package com.web.wlsms.service.system;


import com.github.pagehelper.PageInfo;
import com.web.wlsms.request.SaveRoleRequest;
import com.web.wlsms.request.SimpleRequest;

public interface MenuRoleService {

    int saveRoleAuthConfig(SaveRoleRequest request);

    PageInfo getAuthMenuRole(SimpleRequest request);

}
