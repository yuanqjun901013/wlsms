package com.web.wlsms.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminMenuEntity implements Serializable {

    private Long id;                      //id
    private String sysCode;               //系统代码
    private String name;                  //菜单名
    private String url;                   //url
    private Long parentId;                //父节点ID
    private Short isNeedAuth = 1;           //是否需要权限控制
    private String menuCode;
    private String iconCls;
    private Long level;//菜单级别
    private List<AdminMenuEntity> childTree;
    private boolean checked=false;              //是否选中

}
