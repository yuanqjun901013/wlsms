package com.web.wlsms.response;

import lombok.Data;

import java.util.List;

@Data
public class MenuNodeResponse {
    private Long id;
    private String text;
    private String url;
    private List<MenuNodeResponse> children;
    private String menu;
    private String state;
    private String iconCls;
    private boolean checked;
    private Long level;
}
