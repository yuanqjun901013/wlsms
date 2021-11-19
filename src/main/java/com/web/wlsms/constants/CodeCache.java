package com.web.wlsms.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Component
public class CodeCache {
    public static Map<String, String> codeMap = new HashMap<String, String>();

    @Autowired
//    private ICodeService codeService;

    @PostConstruct
    public void init(){
//        System.out.println("系统启动中。。。加载codeMap");
//        List<Code> codeList = codeService.selectAll();
//        for (Code code : codeList) {
//            codeMap.put(code.getKey(), code.getValue());
//        }
    }

    @PreDestroy
    public void destroy(){
        System.out.println("系统运行结束");
    }
}
