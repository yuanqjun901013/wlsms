package com.web.wlsms.service.mongodb;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.mongodb.*;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        StringBuilder sql = new StringBuilder("SELECT id, wx_name as wxName, car_pol as carPol, dpl_value as dplValue, tkpl_value as tkplValue, " +
                "xh_type as xhType, msl_value as mslValue, build_time as buildTime, zzb_value as zzbValue, tzys_name as tzysName, " +
                "muladdr, others, exmlen, fcycle, flen, cf, rm, sindex, user_properties as userProperties, appear_time as appearTime, "+
                "collection_name as collectionName, mongodb_ip as mongodbIp, mongodb_database as mongodbDatabase, mongo_user as mongoUser, " +
                "mongo_pwd as mongoPwd, bm_type as bmType, ml_name as mlName, status FROM wlsms_mongodb_conf where 1 = 1");
        PageHelper.startPage(request.getPage(), request.getRows());
        List<WlsmsMongodbConf> mongodbConfList = jdbcTemplate.query(sql.toString(), (resultSet, i) -> {
            String id = resultSet.getString("id");
            WlsmsMongodbConf wlsmsMongodbConf = new WlsmsMongodbConf();
            wlsmsMongodbConf.setId(Long.valueOf(id));
            wlsmsMongodbConf.setWxName(resultSet.getString("wxName"));
            wlsmsMongodbConf.setCarPol(resultSet.getString("carPol"));
            wlsmsMongodbConf.setDplValue(resultSet.getString("dplValue"));
            wlsmsMongodbConf.setTkplValue(resultSet.getString("tkplValue"));
            wlsmsMongodbConf.setXhType(resultSet.getString("xhType"));
            wlsmsMongodbConf.setMslValue(resultSet.getString("mslValue"));
//            wlsmsMongodbConf.setBuildTime(resultSet.getString("buildTime"));
            wlsmsMongodbConf.setZzbValue(resultSet.getString("zzbValue"));
            wlsmsMongodbConf.setTzysName(resultSet.getString("tzysName"));
            wlsmsMongodbConf.setCollectionName(resultSet.getString("collectionName"));
            wlsmsMongodbConf.setMongodbIp(resultSet.getString("mongodbIp"));
            wlsmsMongodbConf.setMongodbDatabase(resultSet.getString("mongodbDatabase"));
            wlsmsMongodbConf.setMongoUser(resultSet.getString("mongoUser"));
            wlsmsMongodbConf.setMongoPwd(resultSet.getString("mongoPwd"));
            wlsmsMongodbConf.setBmType(resultSet.getString("bmType"));
            wlsmsMongodbConf.setMlName(resultSet.getString("mlName"));
            wlsmsMongodbConf.setMuladdr(resultSet.getString("muladdr"));
            wlsmsMongodbConf.setOthers(resultSet.getString("others"));
            wlsmsMongodbConf.setExmlen(resultSet.getString("exmlen"));
            wlsmsMongodbConf.setFcycle(resultSet.getString("fcycle"));
            wlsmsMongodbConf.setFlen(resultSet.getString("flen"));
            wlsmsMongodbConf.setCf(resultSet.getString("cf"));
            wlsmsMongodbConf.setRm(resultSet.getString("rm"));
            wlsmsMongodbConf.setSindex(resultSet.getString("sindex"));
            wlsmsMongodbConf.setUserProperties(resultSet.getString("userProperties"));
            wlsmsMongodbConf.setAppearTime(resultSet.getString("appearTime"));
            wlsmsMongodbConf.setStatus(resultSet.getString("status"));
            return wlsmsMongodbConf;
        });
        return new PageInfo<>(mongodbConfList);
    }

    public BaseResponse getTestConnect(WlsmsMongodbConf conf){
//        String sURI = String.format("mongodb://%s:%s@%s:%d/%s", conf.getMongoUser(), conf.getMongoPwd(), conf.getMongodbIp(), 27017, conf.getMongodbDatabase());
//        MongoClientURI uri = new MongoClientURI(sURI);
//        MongoClient mongoClient = new MongoClient(uri);
//        MongoDatabase mongoDatabase = mongoClient.getDatabase(conf.getMongodbDatabase());
//        MongoCollection coll = mongoDatabase.getCollection(conf.getCollectionName());
//        return BaseResponse.ok(coll);
        return BaseResponse.ok();
    }

    public WlsmsMongodbConf getConf(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(500);
        StringBuilder sql = new StringBuilder("SELECT id, wx_name as wxName, car_pol as carPol, dpl_value as dplValue, tkpl_value as tkplValue, " +
                "xh_type as xhType, msl_value as mslValue, build_time as buildTime, zzb_value as zzbValue, tzys_name as tzysName, " +
                "muladdr, others, exmlen, fcycle, flen, cf, rm, sindex, user_properties as userProperties, appear_time as appearTime, "+
                "collection_name as collectionName, mongodb_ip as mongodbIp, mongodb_database as mongodbDatabase, mongo_user as mongoUser, " +
                "mongo_pwd as mongoPwd, bm_type as bmType, ml_name as mlName, status FROM wlsms_mongodb_conf where 1 = 1");
        sql.append(" AND status = 'on'");
        WlsmsMongodbConf wlsmsMongodbConf = new WlsmsMongodbConf();
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql.toString());
        if(null != queryForList && queryForList.size() >0){
            Map<String, Object> map = queryForList.get(0);
            wlsmsMongodbConf = JSON.parseObject(JSON.toJSONString(map), WlsmsMongodbConf.class);
        }
        return wlsmsMongodbConf;
    }
}
