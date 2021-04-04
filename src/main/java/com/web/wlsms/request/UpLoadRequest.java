package com.web.wlsms.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpLoadRequest {

    private String docName;
    private String filePath;
    private String fileName;
    private String userNo;
}
