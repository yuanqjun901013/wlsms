package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminRoleUserEntity {

    private String id;

    private String roleCode;

    private String roleName;

    private String userNo;

    private String menuInfo;

}
