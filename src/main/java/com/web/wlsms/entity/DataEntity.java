package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DataEntity {
  private long id;
  private String sxzfqName;
  private String sxplValue;
  private String bpqplValue;
  private String zplValue;
  private String xxplValue;
  private String systemName;
  private String tzslValue;
  private String xxslValue;
  private String tzfsName;
  private String xdbmCode;
  private String content;
  private String xzbValue;
  private String errorContent;
  private String remark;
  private String cjTime;
  private String wzlValue;
  private String otherUserCode;
  private String otherKxCode;
  private String otherUserTitle;
  private String otherKxTitle;
  private String qrxdValue;
  private String qrxdContent;
  private String zhsjContent;
  private String passportRemark;
  private String mobileUnitValue;
  private String dataValue;
  private String createTime;
  private String editTime;
  private String positionCode;
}
