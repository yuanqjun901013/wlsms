package com.web.wlsms.service.mongodb;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service("MongoService")
public class MongoService {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private DataSource dataSource;
    public BaseResponse saveMongoConfig(WlsmsMongodbConf wlsmsMongodbConf){
        WlsmsMongodbConf mongoConf = mongoRepository.save(wlsmsMongodbConf);
        return BaseResponse.ok(mongoConf);
    }

    public PageInfo getMongoDbList(SimpleRequest<String> request){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(500);
        StringBuilder sql = new StringBuilder("SELECT id, wx_name as wxName, zpl_value as zplValue, dpl_value as dplValue, tkpl_value as tkplValue, " +
                "xh_type as xhType, msl_value as mslValue, build_time as buildTime, zzb_value as zzbValue, tzys_name as tzysName, " +
                "collection_name as collectionName, mongodb_ip as mongodbIp, mongodb_database as mongodbDatabase, mongo_user as mongoUser, " +
                "mongo_pwd as mongoPwd, bm_type as bmType, ml_name as mlName, status FROM wlsms_mongodb_conf where 1 = 1");
        PageHelper.startPage(request.getPage(), request.getRows());
        List<WlsmsMongodbConf> mongodbConfList = jdbcTemplate.query(sql.toString(), (resultSet, i) -> {
            String id = resultSet.getString("id");
            WlsmsMongodbConf wlsmsMongodbConf = new WlsmsMongodbConf();
            wlsmsMongodbConf.setId(Long.valueOf(id));
            wlsmsMongodbConf.setWxName(resultSet.getString("wxName"));
            wlsmsMongodbConf.setZplValue(resultSet.getString("zplValue"));
            wlsmsMongodbConf.setDplValue(resultSet.getString("dplValue"));
            wlsmsMongodbConf.setTkplValue(resultSet.getString("tkplValue"));
            wlsmsMongodbConf.setXhType(resultSet.getString("xhType"));
            wlsmsMongodbConf.setMslValue(resultSet.getString("mslValue"));
            wlsmsMongodbConf.setBuildTime(resultSet.getString("buildTime"));
            wlsmsMongodbConf.setZzbValue(resultSet.getString("zzbValue"));
            wlsmsMongodbConf.setTzysName(resultSet.getString("tzysName"));
            wlsmsMongodbConf.setCollectionName(resultSet.getString("collectionName"));
            wlsmsMongodbConf.setMongodbIp(resultSet.getString("mongodbIp"));
            wlsmsMongodbConf.setMongodbDatabase(resultSet.getString("mongodbDatabase"));
            wlsmsMongodbConf.setMongoUser(resultSet.getString("mongoUser"));
            wlsmsMongodbConf.setMongoPwd(resultSet.getString("mongoPwd"));
            wlsmsMongodbConf.setBmType(resultSet.getString("bmType"));
            wlsmsMongodbConf.setMlName(resultSet.getString("mlName"));
            wlsmsMongodbConf.setStatus(resultSet.getInt("status"));
            return wlsmsMongodbConf;
        });
        return new PageInfo<>(mongodbConfList);
    }
}
