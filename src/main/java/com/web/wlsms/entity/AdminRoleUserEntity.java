package com.web.wlsms.entity;

import lombok.*;

import java.util.List;

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

}
