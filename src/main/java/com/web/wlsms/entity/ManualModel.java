package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ManualModel {
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
    //信噪比
    private String xzbValue;
    //采集时间
    private String buildDate;
    //公文批次号
    private String proCode;
    //地址识别码
    private String positionCode;
    //地址
    private String positionName;
    private long id;
    private String userNo;
    //极化方式
    private String carPol;
    //帧长
    private String flen;
    //备注
    private String remark;
    //码率
    private String mlName;

}
