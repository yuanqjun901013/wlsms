package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TaskInfo {
    private long id;
    private String userNo;
    private String userName;
    private String buildTime;
    private String title;
    private String content;
    private String feedbackContent;
    private Integer state;
    private String receiverUserNo;
    private String receiverUserName;
    private String receiverTime;
    private String feedbackTime;
    private String feedbackUserNo;
    private String feedbackUserName;
    private Integer taskType;
    private String positionCode;
    private String positionName;
}
