package com.web.wlsms.request;

import lombok.Data;

@Data
public class AdminRoleRequest extends PageRequest {

    private String roleCode;

    private String roleName;
}
