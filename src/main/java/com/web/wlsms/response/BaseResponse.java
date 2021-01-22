package com.web.wlsms.response;

/**
 * <p>响应实体.<br>
 *
 *
 */
public class BaseResponse<T> {

    private static final String SUCCESS = "0000";

    private static final String FAIL = "9999";

    private String code;

    private String msg;

    private T data;

    public BaseResponse() {}

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return BaseResponse.SUCCESS.equals(this.code);
    }

    public static <T> BaseResponse<T> ok() {
        return ok(null);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return ok("成功", data);
    }

    public static <T> BaseResponse<T> ok(String msg, T data) {
        return new BaseResponse<T>(SUCCESS, msg, data);
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return fail(FAIL, msg);
    }

    public static <T> BaseResponse<T> fail(String code, String msg) {
        return new BaseResponse<T>(code, msg, null);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
