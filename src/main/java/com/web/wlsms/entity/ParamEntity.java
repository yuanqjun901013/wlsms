package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ParamEntity {
    private long id;
    private String paramType;
    private String paramName;
    private String paramValue;
}
