package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DocManagerEntity {
    private long id;
    private String createTime;
    private String editTime;
    private String docName;
    private String fileName;
    private String filePath;
    private String userNo;
    private String userName;
}
