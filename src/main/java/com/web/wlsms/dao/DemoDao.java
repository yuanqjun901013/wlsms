package com.web.wlsms.dao;

import com.web.wlsms.entity.DemoEntity;

import java.util.List;
import java.util.Map;

public interface DemoDao {
    void saveDemo(DemoEntity demoEntity);

    void removeDemo(Long id);

    void updateDemo(DemoEntity demoEntity);

    DemoEntity findDemoById(Long id);

    List<Map> findAllObject(String collectionName);

}
