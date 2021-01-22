package com.web.wlsms.request;

import lombok.Data;

/**
 * 保存菜单权限
 */
@Data
public class SaveRoleRequest {

    private String roleCode;

    private String menuIds;
}
