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
public class CategoryNodeRelModel {
    private Long rid; //记录ID
    private Long id; //目录ID
    private String name;//目录名称
    private String description;//目录描述
    private String categoryPath;//所属商品目录
    private String salePath;//所属销售目录
    private String createTime; //创建时间
    private Integer onShelfTotal; //上架服务总量
    private Integer showTotal; //上架且门户展示的服务总量
}
