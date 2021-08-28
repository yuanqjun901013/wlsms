package com.web.wlsms.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "wlsms_mongodb_conf")
public class WlsmsMongodbConf {

    private Long id;
    /**
     * 名称
     */
    private String wxName;
    //中频
    private String zplValue;
    //电平
    private String dplValue;
    //天空频率
    private String tkplValue;
    //信号类型
    private String xhType;
    //码速率
    private String mslValue;
    //采集时间
    private String buildTime;
    //载噪比
    private String zzbValue;
    //调制样式
    private String tzysName;
    //collection名称
    private String collectionName;
    //mongodb服务器ip
    private String mongodbIp;
    //database名称
    private String mongodbDatabase;
    //访问用户名
    private String mongoUser;
    //访问登录密码
    private String mongoPwd;
    //编码类型(信道)
    private String bmType;
    //码率(信道)
    private String mlName;
    //是否开启
    private int status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "wx_name")
    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }
    @Column(name = "zpl_value")
    public String getZplValue() {
        return zplValue;
    }

    public void setZplValue(String zplValue) {
        this.zplValue = zplValue;
    }
    @Column(name = "dpl_value")
    public String getDplValue() {
        return dplValue;
    }

    public void setDplValue(String dplValue) {
        this.dplValue = dplValue;
    }
    @Column(name = "tkpl_value")
    public String getTkplValue() {
        return tkplValue;
    }

    public void setTkplValue(String tkplValue) {
        this.tkplValue = tkplValue;
    }
    @Column(name = "xh_type")
    public String getXhType() {
        return xhType;
    }

    public void setXhType(String xhType) {
        this.xhType = xhType;
    }
    @Column(name = "msl_value")
    public String getMslValue() {
        return mslValue;
    }

    public void setMslValue(String mslValue) {
        this.mslValue = mslValue;
    }
    @Column(name = "build_time")
    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
    @Column(name = "zzb_value")
    public String getZzbValue() {
        return zzbValue;
    }

    public void setZzbValue(String zzbValue) {
        this.zzbValue = zzbValue;
    }
    @Column(name = "tzys_name")
    public String getTzysName() {
        return tzysName;
    }

    public void setTzysName(String tzysName) {
        this.tzysName = tzysName;
    }
    @Column(name = "collection_name")
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
    @Column(name = "mongodb_ip")
    public String getMongodbIp() {
        return mongodbIp;
    }

    public void setMongodbIp(String mongodbIp) {
        this.mongodbIp = mongodbIp;
    }
    @Column(name = "mongodb_database")
    public String getMongodbDatabase() {
        return mongodbDatabase;
    }

    public void setMongodbDatabase(String mongodbDatabase) {
        this.mongodbDatabase = mongodbDatabase;
    }
    @Column(name = "mongo_user")
    public String getMongoUser() {
        return mongoUser;
    }

    public void setMongoUser(String mongoUser) {
        this.mongoUser = mongoUser;
    }
    @Column(name = "mongo_pwd")
    public String getMongoPwd() {
        return mongoPwd;
    }

    public void setMongoPwd(String mongoPwd) {
        this.mongoPwd = mongoPwd;
    }

    @Column(name = "bm_type")
    public String getBmType() {
        return bmType;
    }

    public void setBmType(String bmType) {
        this.bmType = bmType;
    }

    @Column(name = "ml_name")
    public String getMlName() {
        return mlName;
    }

    public void setMlName(String mlName) {
        this.mlName = mlName;
    }
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
