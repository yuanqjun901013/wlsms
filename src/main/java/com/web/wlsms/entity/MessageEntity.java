package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageEntity {
    private long id;
    private String userNo;
    private String buildTime;
    private String title;
    private String content;
}
