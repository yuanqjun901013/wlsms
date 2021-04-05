package com.web.wlsms.request;

import lombok.Data;

import java.util.List;

/**
 * excel读取共通Result<br>
 * 〈功能详细描述〉
 *
 * @author 17091073
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class ExcelReadResult<T> {

    private boolean status;

    private List<T> result;
}
