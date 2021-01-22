package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TokenEntity {
    private long id;
    private String userNo;
    private String token;
    private String buildTime;
}
