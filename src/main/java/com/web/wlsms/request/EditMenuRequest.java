package com.web.wlsms.request;

import lombok.Data;

@Data
public class EditMenuRequest {

    private String id;
    private String sysCode;
    private String name;
    private String url;
    private String isNeedAuth;
    private String menuCode;

}
