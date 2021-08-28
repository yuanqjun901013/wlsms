package com.web.wlsms.service.mongodb;

import com.web.wlsms.entity.WlsmsMongodbConf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MongoRepository extends JpaRepository<WlsmsMongodbConf, Integer> {
}
