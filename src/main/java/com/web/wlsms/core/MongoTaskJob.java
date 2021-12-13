package com.web.wlsms.core;

import com.alibaba.fastjson.JSON;
import com.web.wlsms.controller.data.MacAutoController;
import com.web.wlsms.entity.MachineModel;
import com.web.wlsms.entity.ManualModel;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.entity.WlsmsMongodbConf;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.data.DataBuildNewService;
import com.web.wlsms.service.data.MacAutoService;
import com.web.wlsms.service.mongodb.MongoService;
import com.web.wlsms.service.system.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
/**
 * 开启定时任务的注解
 */
@EnableScheduling
public class MongoTaskJob {
    @Resource
    private MongoService mongoService;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private MacAutoService macAutoService;
    @Resource
    private DataBuildNewService dataBuildNewService;
    @Autowired
    MessageService messageService;
    /**
     * 十分钟获取一次mongo生成的机器数据
     */
//    @Scheduled(cron = "*/10 * * * * ?")
////    @Scheduled(cron = "0/50 * * * * ?")
//    public void job2(){
//      log.info("自动获取mongo数据定时任务" + new Date()+" start:");
//      //查询mongo数据库配置
//        WlsmsMongodbConf conf = mongoService.getConf();
//        if(null != conf && conf.getStatus().equals("on")){
////            List<Map> obList = mongoTemplate.findAll(Map.class, conf.getCollectionName());
////            Query query = new Query(Criteria.where("Carraer Type").regex("w"));
////            List<Map> collections = mongoTemplate.find(query, Map.class, conf.getCollectionName());
////            System.out.println("定时任务" + JSON.toJSONString(collections));
//            Date date=new Date();
//            Calendar calendar=Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.MINUTE, -10);
////            calendar.add(Calendar.DAY_OF_MONTH, -1);//一天前
//            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            //获取十分钟之前的数据
//            String startTime = spf.format(calendar.getTime());
//            String endTime = spf.format(date);
//            //连接mongo数据库
//            Criteria criteria = new Criteria();
//            Query query = new Query(criteria.and("appearTime").gte(startTime).lte(endTime));
//            log.info("自动获取mongo集合:" + conf.getCollectionName()+";");
////            List<Map> collections = mongoTemplate.find(query, Map.class, conf.getCollectionName()); //根据条件查询
//            List<Map> collections = mongoTemplate.findAll(Map.class, conf.getCollectionName());
//            log.info("自动获取mongo collections:" + JSON.toJSONString(collections) +";");
//            List<MachineModel> machineModelList = new ArrayList<>();
//            if(null != collections && collections.size() > 0){
//                String proCode = getProCodeNum();
//                //获取当前日期
//                SimpleDateFormat bartDateFormat =
//                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date dateNow = new Date();
//                String buildTime = bartDateFormat.format(dateNow);
//                for(Map map:collections){
//                    MachineModel machineModel = new MachineModel();
//                    machineModel.setWxName(StringUtils.isBlank(String.valueOf(map.get(conf.getWxName()))) == true ? "0" : String.valueOf(map.get(conf.getWxName())));
//                    machineModel.setCarPol(StringUtils.isBlank(String.valueOf(map.get(conf.getCarPol()))) == true ? "0" : String.valueOf(map.get(conf.getCarPol())));
//                    machineModel.setDplValue(StringUtils.isBlank(String.valueOf(map.get(conf.getDplValue()))) == true ? "0" : String.valueOf(map.get(conf.getDplValue())));
//                    machineModel.setTkplValue(StringUtils.isBlank(String.valueOf(map.get(conf.getTkplValue()))) == true ? "0" : String.valueOf(map.get(conf.getTkplValue())));
//                    machineModel.setXhType(StringUtils.isBlank(String.valueOf(map.get(conf.getTkplValue()))) == true ? "0" : String.valueOf(map.get(conf.getTkplValue())));
//                    machineModel.setMslValue(StringUtils.isBlank(String.valueOf(map.get(conf.getMslValue()))) == true ? "0" : String.valueOf(map.get(conf.getMslValue())));
//                    machineModel.setZzbValue(StringUtils.isBlank(String.valueOf(map.get(conf.getZzbValue()))) == true ? "0" : String.valueOf(map.get(conf.getZzbValue())));
//                    machineModel.setTzysName(StringUtils.isBlank(String.valueOf(map.get(conf.getTzysName()))) == true ? "0" : String.valueOf(map.get(conf.getTzysName())));
//                    machineModel.setBmType(StringUtils.isBlank(String.valueOf(map.get(conf.getBmType()))) == true ? "0" : String.valueOf(map.get(conf.getBmType())));
//                    machineModel.setMlName(StringUtils.isBlank(String.valueOf(map.get(conf.getMlName()))) == true ? "0" : String.valueOf(map.get(conf.getMlName())));
//                    machineModel.setMuladdr(StringUtils.isBlank(String.valueOf(map.get(conf.getMuladdr()))) == true ? "0" : String.valueOf(map.get(conf.getMuladdr())));
//                    machineModel.setOthers(StringUtils.isBlank(String.valueOf(map.get(conf.getOthers()))) == true ? "0" : String.valueOf(map.get(conf.getOthers())));
//                    machineModel.setExmlen(StringUtils.isBlank(String.valueOf(map.get(conf.getExmlen()))) == true ? "0" : String.valueOf(map.get(conf.getExmlen())));
//                    machineModel.setFcycle(StringUtils.isBlank(String.valueOf(map.get(conf.getFcycle()))) == true ? "0" : String.valueOf(map.get(conf.getFcycle())));
//                    machineModel.setFlen(StringUtils.isBlank(String.valueOf(map.get(conf.getFlen()))) == true ? "0" : String.valueOf(map.get(conf.getFlen())));
//                    machineModel.setCf(StringUtils.isBlank(String.valueOf(map.get(conf.getCf()))) == true ? "0" : String.valueOf(map.get(conf.getCf())));
//                    machineModel.setRm(StringUtils.isBlank(String.valueOf(map.get(conf.getRm()))) == true ? "0" : String.valueOf(map.get(conf.getRm())));
//                    machineModel.setSindex(StringUtils.isBlank(String.valueOf(map.get(conf.getSindex()))) == true ? "0" : String.valueOf(map.get(conf.getSindex())));
//                    machineModel.setUserProperties(StringUtils.isBlank(String.valueOf(map.get(conf.getUserProperties()))) == true ? "0" : String.valueOf(map.get(conf.getUserProperties())));
//                    machineModel.setAppearTime(StringUtils.isBlank(String.valueOf(map.get(conf.getAppearTime()))) == true ? "0" : String.valueOf(map.get(conf.getAppearTime())));
//                    machineModel.setPositionCode("1");
//                    machineModel.setUserNo("admin");
//                    machineModel.setBuildTime(buildTime);
//                    machineModel.setProCode(proCode);
//                    machineModel.setBuildType("机器采集");
//                    //简化码率分数(mlName)
//                    machineModel.setMlName(getMlName(machineModel.getMlName()));
//                    machineModelList.add(machineModel);
//                }
//                List<MachineModel> newMachineList = new ArrayList<>();
//                //筛选数据是否在数据库中存在
//                if(null != machineModelList && machineModelList.size() > 0){
//                    for(MachineModel machineModel:machineModelList){
//                        int count = macAutoService.queryMachineCountByInfo(machineModel);
//                        if(count == 0){
//                            newMachineList.add(machineModel);
//                        }
//                    }
//                }
//
//                if(null != newMachineList && newMachineList.size() > 0){
//                    int num = 0;
//                    //排重
//                    Set<MachineModel> setData = new HashSet<>();
//                    setData.addAll(newMachineList);
//                    List<MachineModel> newAddData= new ArrayList<>();
//                    newAddData.addAll(setData);
//                    //分批次 批量保存
//                    if (newAddData.size() > 200) {
//                        long total = newAddData.size();
//                        long remain = total % 200;
//                        long times = total / 200;
//                        long realTimes = remain == 0 ? times : times + 1;
//                        for (long i = 0; i < realTimes; i++) {
//                            List<MachineModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
//                            num += macAutoService.insertMachine(batchList);
//                        }
//                    } else {
//                        num += macAutoService.insertMachine(newAddData);
//                    }
//                    //自动比对人工数据(获取最新日期的人工数据)
//                    String buildDate = macAutoService.queryLimitDate();
//                    List<ManualModel> getManualList = macAutoService.getManualListByDate(buildDate);
//                    //处理对比
//                    try {
//                        macAutoService.getAutoDocker(newAddData, getManualList, "系统");
//                    }catch (Exception e){
//                    }
//                    if(num > 0){
//                        MessageEntity messageEntity = new MessageEntity();
//                        messageEntity.setUserNo("admin");
//                        messageEntity.setTitle("定时获取mongodb机器数据");
//                        messageEntity.setContent("admin"+":获取mongodb机器数据,公文号为"+proCode);
//                        messageEntity.setOperationType(1);
//                        messageService.insertMessage(messageEntity);
//                    }
//                }
//            }
//        }
//      log.info("自动获取mongo数据定时任务" + new Date()+" end:");
//    }

    @Scheduled(cron = "*/10 * * * * ?")
//    @Scheduled(cron = "0/50 * * * * ?")
    public void job2(){
        log.info("自动获取mongo数据定时任务" + new Date()+" start:");
        //查询mongo数据库配置
        WlsmsMongodbConf conf = mongoService.getConf();
        if(null != conf && conf.getStatus().equals("on")){
//            List<Map> obList = mongoTemplate.findAll(Map.class, conf.getCollectionName());
//            Query query = new Query(Criteria.where("Carraer Type").regex("w"));
//            List<Map> collections = mongoTemplate.find(query, Map.class, conf.getCollectionName());
//            System.out.println("定时任务" + JSON.toJSONString(collections));
            Date date=new Date();
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, -10);
//            calendar.add(Calendar.DAY_OF_MONTH, -1);//一天前
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取十分钟之前的数据
            String startTime = spf.format(calendar.getTime());
            String endTime = spf.format(date);
            //连接mongo数据库
            Criteria criteria = new Criteria();
            Query query = new Query(criteria.and("appearTime").gte(startTime).lte(endTime));
            log.info("自动获取mongo集合:" + conf.getCollectionName()+";");
//            List<Map> collections = mongoTemplate.find(query, Map.class, conf.getCollectionName()); //根据条件查询
            List<Map> collections = mongoTemplate.findAll(Map.class, conf.getCollectionName());
            log.info("自动获取mongo collections:" + JSON.toJSONString(collections) +";");
            List<MachineModel> machineModelList = new ArrayList<>();
            if(null != collections && collections.size() > 0){
                String proCode = getProCodeNum();
                //获取当前日期
                SimpleDateFormat bartDateFormat =
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateNow = new Date();
                String buildTime = bartDateFormat.format(dateNow);
                for(Map map:collections){
                    MachineModel machineModel = new MachineModel();
                    machineModel.setWxName(StringUtils.isBlank(String.valueOf(map.get(conf.getWxName()))) == true ? "0" : String.valueOf(map.get(conf.getWxName())));
                    machineModel.setCarPol(StringUtils.isBlank(String.valueOf(map.get(conf.getCarPol()))) == true ? "0" : String.valueOf(map.get(conf.getCarPol())));
                    machineModel.setDplValue(StringUtils.isBlank(String.valueOf(map.get(conf.getDplValue()))) == true ? "0" : String.valueOf(map.get(conf.getDplValue())));
                    machineModel.setTkplValue(StringUtils.isBlank(String.valueOf(map.get(conf.getTkplValue()))) == true ? "0" : String.valueOf(map.get(conf.getTkplValue())));
                    machineModel.setXhType(StringUtils.isBlank(String.valueOf(map.get(conf.getXhType()))) == true ? "0" : String.valueOf(map.get(conf.getXhType())));
                    machineModel.setMslValue(StringUtils.isBlank(String.valueOf(map.get(conf.getMslValue()))) == true ? "0" : String.valueOf(map.get(conf.getMslValue())));
                    machineModel.setZzbValue(StringUtils.isBlank(String.valueOf(map.get(conf.getZzbValue()))) == true ? "0" : String.valueOf(map.get(conf.getZzbValue())));
                    machineModel.setTzysName(StringUtils.isBlank(String.valueOf(map.get(conf.getTzysName()))) == true ? "0" : String.valueOf(map.get(conf.getTzysName())));
                    machineModel.setBmType(StringUtils.isBlank(String.valueOf(map.get(conf.getBmType()))) == true ? "0" : String.valueOf(map.get(conf.getBmType())));
                    machineModel.setMlName(StringUtils.isBlank(String.valueOf(map.get(conf.getMlName()))) == true ? "0" : String.valueOf(map.get(conf.getMlName())));
                    machineModel.setMuladdr(StringUtils.isBlank(String.valueOf(map.get(conf.getMuladdr()))) == true ? "0" : String.valueOf(map.get(conf.getMuladdr())));
                    machineModel.setOthers(StringUtils.isBlank(String.valueOf(map.get(conf.getOthers()))) == true ? "0" : String.valueOf(map.get(conf.getOthers())));
                    machineModel.setExmlen(StringUtils.isBlank(String.valueOf(map.get(conf.getExmlen()))) == true ? "0" : String.valueOf(map.get(conf.getExmlen())));
                    machineModel.setFcycle(StringUtils.isBlank(String.valueOf(map.get(conf.getFcycle()))) == true ? "0" : String.valueOf(map.get(conf.getFcycle())));
                    machineModel.setFlen(StringUtils.isBlank(String.valueOf(map.get(conf.getFlen()))) == true ? "0" : String.valueOf(map.get(conf.getFlen())));
                    machineModel.setCf(StringUtils.isBlank(String.valueOf(map.get(conf.getCf()))) == true ? "0" : String.valueOf(map.get(conf.getCf())));
                    machineModel.setRm(StringUtils.isBlank(String.valueOf(map.get(conf.getRm()))) == true ? "0" : String.valueOf(map.get(conf.getRm())));
                    machineModel.setSindex(StringUtils.isBlank(String.valueOf(map.get(conf.getSindex()))) == true ? "0" : String.valueOf(map.get(conf.getSindex())));
                    machineModel.setUserProperties(StringUtils.isBlank(String.valueOf(map.get(conf.getUserProperties()))) == true ? "0" : String.valueOf(map.get(conf.getUserProperties())));
                    machineModel.setAppearTime(StringUtils.isBlank(String.valueOf(map.get(conf.getAppearTime()))) == true ? "0" : String.valueOf(map.get(conf.getAppearTime())));
                    machineModel.setPositionCode("1");
                    machineModel.setUserNo("admin");
                    machineModel.setBuildTime(buildTime);
                    machineModel.setProCode(proCode);
                    machineModel.setBuildType("机器采集");
                    //简化码率分数(mlName)
                    machineModel.setMlName(getMlName(machineModel.getMlName()));
                    machineModelList.add(machineModel);
                }
                List<MachineModel> newMachineList = new ArrayList<>();
                //筛选数据是否在数据库中存在
                if(null != machineModelList && machineModelList.size() > 0){
                    for(MachineModel machineModel:machineModelList){
                        int count = dataBuildNewService.queryMachineCountByInfo(machineModel);
                        if(count == 0){
                            newMachineList.add(machineModel);
                        }
                    }
                }

                if(null != newMachineList && newMachineList.size() > 0){
                    int num = 0;
                    //排重
                    Set<MachineModel> setData = new HashSet<>();
                    setData.addAll(newMachineList);
                    List<MachineModel> newAddData= new ArrayList<>();
                    newAddData.addAll(setData);
                    //分批次 批量保存
                    if (newAddData.size() > 200) {
                        long total = newAddData.size();
                        long remain = total % 200;
                        long times = total / 200;
                        long realTimes = remain == 0 ? times : times + 1;
                        for (long i = 0; i < realTimes; i++) {
                            List<MachineModel> batchList = newAddData.stream().skip(i * 200).limit(200).collect(Collectors.toList());
                            num += dataBuildNewService.insertMachine(batchList);
                        }
                    } else {
                        num += dataBuildNewService.insertMachine(newAddData);
                    }
                    //自动比对人工数据(获取最新日期的人工数据)
                    String buildDate = dataBuildNewService.queryLimitDate();
                    List<MachineModel> getManualList = dataBuildNewService.getManualListByDate(buildDate);
                    //处理对比
                    try {
                        dataBuildNewService.getAutoDocker(newAddData, getManualList, "系统");
                    }catch (Exception e){
                    }
                    if(num > 0){
                        MessageEntity messageEntity = new MessageEntity();
                        messageEntity.setUserNo("admin");
                        messageEntity.setTitle("定时获取mongodb机器数据");
                        messageEntity.setContent("admin"+":获取mongodb机器数据,公文号为"+proCode);
                        messageEntity.setOperationType(1);
                        messageService.insertMessage(messageEntity);
                    }
                }
            }
        }
        log.info("自动获取mongo数据定时任务" + new Date()+" end:");
    }

    /**
     * 生成公文号：5位项目名称 + 17为时间戳 + 6位随机数字
     *
     * @return 随机唯一字符串
     */
    private String getProCodeNum() {

        StringBuilder stringBuilder = new StringBuilder();
        String dateStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        //获取十位随机数
        String randomStr = getNumberRandom(6);

        return stringBuilder.append("WLSMS").append(dateStr).append("-").append(randomStr).toString();
    }
    /**
     * 生成随机数字
     *
     * @param length 生成随机数的长度
     * @return 随机唯一字符串
     */
    private String getNumberRandom(int length) {
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < length; i++) {
            SecureRandom random = new SecureRandom();
            // 输出字母还是数字
            if (random.nextBoolean()) {
                // 输出大写或小写字母
                char temp = random.nextBoolean() ? 'a' : 'A';
                val.append((char) (random.nextInt(26) + temp));
            } else {
                // 输出数字
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    //简化分数
    private String getMlName(String mlNameOld){
        if(StringUtils.isBlank(mlNameOld)){
            return "未知";
        }
        String[] mlArr= mlNameOld.split("/");
        if(mlArr.length > 1){
            boolean isNumA = mlArr[1].matches("[0-9]+");
            boolean isNumB = mlArr[0].matches("[0-9]+");
            if(isNumA && isNumB) {
                int a = Integer.parseInt(mlArr[1]), b = Integer.parseInt(mlArr[0]);//a 是分母
                int gcd = gcd(a, b);
//            System.out.println(b / gcd + "/" + a / gcd); // 输出了 5/6
                return b / gcd + "/" + a / gcd;
            }else {
                return mlNameOld;
            }
        }else {
            return mlNameOld;
        }

    }

    private static int gcd(int x, int y){ // 这个是运用辗转相除法求 两个数的 最大公约数 看不懂可以百度 // 下
        if(y == 0)
            return x;
        else
            return gcd(y,x%y);
    }
}
