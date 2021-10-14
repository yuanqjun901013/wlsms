package com.web.wlsms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TableEntity
 * @Description TODO
 * @Author 18086134
 * @Date 2019/12/12 20:21
 * @VERSION V1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableEntity {
    private String type;
    private String tableName;
    private String values;
    private String params;
}
