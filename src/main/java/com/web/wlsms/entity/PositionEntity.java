package com.web.wlsms.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PositionEntity {
    private long id;
    private String positionName;
    private String positionCode;
}
