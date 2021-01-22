package com.web.wlsms.request;

import lombok.Data;

@Data
public class SimpleRequest<T> extends PageRequest {

    private T request;

}
