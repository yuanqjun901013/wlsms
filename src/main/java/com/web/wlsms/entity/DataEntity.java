package com.web.wlsms.entity;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DataEntity {
  private long id;
  //上行转发器
  private String sxzfqName;
  //上行频率
  private String sxplValue;
  //变频器频率
  private String bpqplValue;
  //中频
  private String zplValue;
  //下行频率
  private String xxplValue;
  //系统
  private String systemName;
  //调制速率
  private String tzslValue;
  //信息速率
  private String xxslValue;
  //调制方式
  private String tzfsName;
  //信道编码
  private String xdbmCode;
  private String content;
  //信噪比
  private String xzbValue;
  //错误内容
  private String errorContent;
  //备注
  private String remark;
  //采集时间
  private String cjTime;
  //误帧率
  private String wzlValue;
  //额外用户数据序列
  private String otherUserCode;
  //额外开销数据序列
  private String otherKxCode;
  //额外用户数据指示
  private String otherUserTitle;
  //额外开销数据指示
  private String otherKxTitle;
  //嵌入信道
  private String qrxdValue;
  //嵌入信道内容
  private String qrxdContent;
  //载荷数据内容
  private String zhsjContent;
  //密码情况
  private String passportRemark;
  //移动属性判定
  private String mobileUnitValue;
  private String createTime;
  private String editTime;
  private String positionCode;
  private String proCode;
  private String userNo;
  //上一日建议上行频率
  private String reSxplValue;
  //上一日建议变频器频率
  private String reBpqplValue;
  //上一日建议中频
  private String reZplValue;
  //上一日建议下行频率
  private String reXxplValue;
  //上一日建议调制速率
  private String reTzslValue;
  //上一日建议信息速率
  private String reXxslValue;
  //上一日建议信噪比
  private String reXzbValue;
  //上一日建议误帧率
  private String reWzlValue;

}
