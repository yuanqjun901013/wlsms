package com.web.wlsms.entity;

import lombok.*;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DataChartModel {
  private String name;
  private String type;
  private List<Long> data;
}
