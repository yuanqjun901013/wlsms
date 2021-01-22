package com.web.wlsms.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TraceDataResponse {
    private String id;
    private String traceId;
    private String traceType;
    private String traceName;
    private String direction;
    private String retry;
    private String url;
    private String protocol;
    private String request;
    private LocalDateTime requestTime;
    private String response;
    private LocalDateTime responseTime;
    private Long processTime;
    private String operator;
}
