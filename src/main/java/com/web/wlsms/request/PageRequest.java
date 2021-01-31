package com.web.wlsms.request;

import lombok.Data;

@Data
public class PageRequest {

    private int pageNumber = 1 ;

    private int pageSize = 10;
    
}
