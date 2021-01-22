package com.web.wlsms.request;

import lombok.Data;

@Data
public class MenuRequest {

    //菜单id
    private String id;

    //根节点名称
    private String parentName;

    //系统代码
    private String sysCode;

    //菜单名称
    private String name;

    //菜单路径
    private String url;

    //是否需要权限控制：0-不要，1-要
    private String isNeedAuth;

    //菜单编码
    private String menuCode;
}
