package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReDataValue {
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
