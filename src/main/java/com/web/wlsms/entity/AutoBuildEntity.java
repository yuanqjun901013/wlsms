package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AutoBuildEntity {
    private long id;
    private String buildDate;
    private String buildTime;
    private String remark;
    private Integer count;
    private String createTime;
    private String editTime;
}
