package com.web.wlsms.response;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonCategoryModel {
    private CategoryNodeModel categoryNode; //类目节点信息
    private PageInfo pageInfo; //子集节点分页数据
}