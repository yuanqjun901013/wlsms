package com.web.wlsms.request;

import lombok.Data;

@Data
public class SimpleRequest<T> extends PageRequest {

    private T request;

    private String queryBt;
    private String startTime;
    private String endTime;
    private String buildDate;
    private String buildTime;

}
