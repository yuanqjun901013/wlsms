package com.web.wlsms.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Document(collection = "ABC_DataCount")
public class AbcDataCount {
    @Id
    private Long id;

    private String SatePlatName;

    private String freq;

    private String SNR;

    private String MulAdrmode;

    private String CarraerType;

    private String ModulateRate;

    private String FrameLen;

    private String CodeMode;

    private String CodeRate;

    private String SignalType;

    private String XINXIFENZU;

    private String IsRaoMa;

    private String AppearTime;

    private String DisAppearTime;
}
