package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MachineDataModel {
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
    private String cjTime;
}
