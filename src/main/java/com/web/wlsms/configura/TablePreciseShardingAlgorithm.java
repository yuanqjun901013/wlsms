package com.web.wlsms.configura;

import com.web.wlsms.utils.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class TablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> preciseShardingValue) {
        StringBuffer tableName = new StringBuffer();
        tableName.append(preciseShardingValue.getLogicTableName())
                .append("_").append(DateUtil.date2Str(dateValue(preciseShardingValue.getValue()), DateUtil.TIME_MONEN));
        return tableName.toString();
    }


    /**
     * 日期转换
     * @param date
     * @return
     */
    public static Date dateValue(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date);
        }catch (Exception e){
            return new Date();
        }
    }
}
