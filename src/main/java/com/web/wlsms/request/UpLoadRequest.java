package com.web.wlsms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpLoadRequest {

    @ApiModelProperty("资源名称")
    private String docName;
    @ApiModelProperty("路径")
    private String filePath;
    @ApiModelProperty("文件名")
    private String fileName;
    @ApiModelProperty("账号")
    private String userNo;
}
