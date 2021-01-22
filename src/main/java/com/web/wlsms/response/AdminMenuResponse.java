package com.web.wlsms.response;

import lombok.Data;

import java.util.List;

@Data
public class AdminMenuResponse {

    private Long id;

    private String sysCode;

    private String name;

    private String url;

    private Long parentId;

    private short isNeedAuth;

    private String menuCode;

    private List<AdminMenuResponse> childTree;

    private boolean checked = false;
}
