package com.web.wlsms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>结果码</p>
 *
 * @author 17091073.
 * @version 2018/8/8.
 */
@AllArgsConstructor
public enum ResultCode {

    /**
     *  成功：00号段
     *  业务异常：10~80号段
     *  系统异常：99号段
     */
    SUCCESS("成功", "0000"),


    TS_EPP_RESP_SUCC("成功","E0000"),
    TS_EPP_RESP_SIGN_FAIL("验签失败","E0001"),
    TS_EPP_RESP_PARAM_ERROR("请求参数不合法","E0002"),
    TS_EPP_RESP_SSF_FAIL("SSF调用失败","E0003"),
    TS_EPP_RESP_ORDER_MEMBER_FAIL("会员查询失败","E0004"),
    TS_EPP_RESP_ORDER_FR_FAIL("分润信息错误","E0005"),
    TS_EPP_RESP_ORDER_FR_MEMBER_FAIL("分润商户号不存在","E0006"),
    TS_EPP_RESP_REQ_LIMIT("当前流量过多,请求被限制","E0007"),
    TS_EPP_RESP_ORDER_NOT_EXISTS("该订单不存在","E2000"),
    TS_EPP_RESP_ORDER_PARAM_NULL("入参为空","E2005"),
    TS_EPP_RESP_PARAM_ERROR1("请求参数不合法","E2011"),

    /**
     * 服务相关
     */
    PRODUCT_NOT_EXISTS("服务不存在", "3000"),
    PRODUCT_NOT_EXISTS_OR_OFF_SHELF("服务不存在或存在已下架的服务{0}", "3001"),
    PRODUCT_CALCULATE_PRICE_ERROR("核价失败", "3002"),

    /**
     * 商户信息账号相关
     */
    ACCOUNT_PARAM_IS_INVALID ("请求参数不合法", "4000"),

    /**
     * 业务公共异常
     */
    SYS_MISSING_REQUEST("请求对象为空", "9000"),
    SYS_PARAM_VALIDATE_FAIL("参数校验失败{0}", "9001"),
    SYS_BUSINESS_PARAM_PARSE_FAIL("业务参数有误", "9002"),
    SYS_BUSINESS_PARAM_VALIDATE_FAIL("业务参数必填校验失败", "9003"),
    SYS_METHOD_RUN_EXCEPTION("业务方法执行异常", "9004"),
    SYS_BUSINESS_DUPLICATE_EXCEPTION("业务数据重复{0}", "9005"),

    FAIL("系统异常", "9999");

    @Getter
    private String message;
    @Getter
    private String code;

}
