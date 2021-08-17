package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AutoDataEntity {
    private long id;
    //下行频率
    private String xxplValue;
    //天空频率
    private String tkplValue;
    //下行天空差值
    private String xtdValue;
    //系统
    private String systemName;
    //信号类型
    private String xhType;
    //调制速率
    private String tzslValue;
    //码速率
    private String mslValue;
    //调制码速率差值
    private String tzdValue;
    //调制方式
    private String tzfsName;
    //调制样式
    private String tzysName;
    //信道编码
    private String xdbmCode;
    //编码类型(信道)
    private String bmType;
    //码率(信道)
    private String mlName;
    //信噪比
    private String xzbValue;
    //载噪比
    private String zzbValue;
    //人工登记时间
    private String buildDate;
    //机器采集时间
    private String buildTime;
    //信息速率
    private String xxslValue;
    //单次策略1
    private String oneCl;
    //单次策略2
    private String twoCl;
    //登录用户
    private String userNo;
    private String createTime;
    private String editTime;
}
