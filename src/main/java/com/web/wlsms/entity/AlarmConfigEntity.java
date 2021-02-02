package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AlarmConfigEntity {
  private long id;
  private String alarmCode;
  private String alarmName;
  private String alarmStartValue;
  private String alarmEndValue;

}
