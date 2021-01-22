package com.web.wlsms.response;

import lombok.*;

/**
 * 服务类目对象
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNodeModel {
    private Long id; //目录ID
    private String name;//目录名称
    private Integer categoryOrder;//目录顺序
    private String description;//目录描述
    private String modifyTime; //修改时间
    private Integer total; //服务总量
    private Integer onShelfTotal; //上架服务总量
}
