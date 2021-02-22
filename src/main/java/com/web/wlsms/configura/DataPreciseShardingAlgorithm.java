package com.web.wlsms.configura;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class DataPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        StringBuffer tableName = new StringBuffer();
        String timeMonth = date2Str(preciseShardingValue.getValue(), "yyyyMM");
        tableName.append(preciseShardingValue.getLogicTableName()).append(timeMonth);
        return tableName.toString();
    }

    public static String date2Str(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        try {
            Date dateTime = dateFormat.parse(date);
            return sdf.format(dateTime);
        }catch (Exception e){
            return sdf.format(new Date());
        }
    }
}
