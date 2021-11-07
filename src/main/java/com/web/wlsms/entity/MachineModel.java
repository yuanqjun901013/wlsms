package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MachineModel {
    private String numb;
    //wx名称
    private String wxName;
//    //中频
//    private String zplValue;
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
    //登记日期
    private String buildDate;
    //编码类型(信道)
    private String bmType;
    //码率(信道)
    private String mlName;
    //公文批次号
    private String proCode;
    //地址识别码
    private String positionCode;
    //地址
    private String positionName;
    private long id;
    private String userNo;
    //数据生成方式:手动导入/机器采集
    private String buildType;
    //极化方式
    private String carPol;
    //多址方式
    private String muladdr;
    //其他
    private String others;
    //分组长度
    private String exmlen;
    //突发周期
    private String fcycle;
    //帧长
    private String flen;
    //差分
    private String cf;
    //扰码
    private String rm;
    //索引号
    private String sindex;
    //用户属性
    private String userProperties;
    //发现时间
    private String appearTime;

    private String createTime;

    private String editTime;

    private String titleOs;
}
