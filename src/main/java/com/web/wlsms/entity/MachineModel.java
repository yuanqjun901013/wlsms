package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MachineModel {
    //wx名称
    private String wxName;
    //中频
    private String zplValue;
    //电平
    private String dplValue;
    //天空频率
    private String tkplValue;
    //信号类型
    private String xhType;
    //码速率
    private String mslValue;
    //载噪比
    private String zzbValue;
    //调制样式
    private String tzysName;
    //采集时间
    private String buildTime;
    //编码类型(信道)
    private String bmType;
    //码率(信道)
    private String mlName;
    //公文批次号
    private String proCode;
    //编码
    private String positionCode;
    private long id;
    private String userNo;
}
