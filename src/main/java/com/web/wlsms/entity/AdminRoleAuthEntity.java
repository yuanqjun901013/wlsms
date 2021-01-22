package com.web.wlsms.entity;

import lombok.*;

import java.io.Serializable;


/**
 * 角色权限表
 * @author 15100468
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminRoleAuthEntity implements Serializable {
    private Long id;
    private String roleCode;  //角色代码
    private Long menuId;     //菜单ID

}
