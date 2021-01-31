package com.web.wlsms.request;

import lombok.Data;

@Data
public class PageRequest {

    private int page = 1 ;

    private int rows = 10;
    
}
