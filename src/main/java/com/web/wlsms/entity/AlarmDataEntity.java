package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AlarmDataEntity {
  private long id;
  private String alarmTitle;
  private String alarmContent;
  private String buildTime;
}
