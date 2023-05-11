package com.web.wlsms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleRequest<T> extends PageRequest {

    private T request;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("标题")
    private String titleOs;

    @ApiModelProperty("关键字")
    private String queryBt;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("发生日期")
    private String buildDate;

    @ApiModelProperty("发生时间")
    private String buildTime;

    @ApiModelProperty("用户编号")
    private String userNo;

    @ApiModelProperty("状态")
    private Integer state;

    @ApiModelProperty("任务类型")
    private Integer taskType;

}
