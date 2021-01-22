package com.web.wlsms.response;

import com.web.wlsms.enums.ResultCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * <p>公共响应实体.<br>
 *
 * @author 17091073
 */
@Getter
@ToString
public class XsResult<T> implements Serializable {

    private static final long serialVersionUID = -3578233942679190233L;

    private String code;

    private String msg;

    private XsResult() {
    }

    private T data;

    public XsResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }

    public static <T> XsResult<T> ok() {
        return ok(null);
    }

    public static <T> XsResult<T> ok(T data) {
        return ok(ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> XsResult<T> ok(String msg, T data) {
        return new XsResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> XsResult<T> fail(String msg) {
        return fail(ResultCode.FAIL.getCode(), msg);
    }

    public static <T> XsResult<T> fail(ResultCode result) {
        return fail(result.getCode(), result.getMessage());
    }

    public static <T> XsResult<T> fail(ResultCode resultCode, Object... arguments) {
        return fail(resultCode.getCode(), MessageFormat.format(resultCode.getMessage(), arguments));
    }

    public static <T> XsResult<T> fail(String code, String msg) {
        return new XsResult<T>(code, msg, null);
    }
}