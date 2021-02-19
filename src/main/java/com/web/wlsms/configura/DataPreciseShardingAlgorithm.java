package com.web.wlsms.configura;

import com.web.wlsms.utils.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

public class DataPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
        StringBuffer tableName = new StringBuffer();
        tableName.append(preciseShardingValue.getLogicTableName())
                .append("_").append(DateUtil.date2Str(preciseShardingValue.getValue(), DateUtil.TIME_MONEN));
        return tableName.toString();
    }
}
