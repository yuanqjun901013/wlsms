package com.web.wlsms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageRequest {
    @ApiModelProperty("页码")
    private int page = 1 ;

    @ApiModelProperty("行数")
    private int rows = 10;
    
}
