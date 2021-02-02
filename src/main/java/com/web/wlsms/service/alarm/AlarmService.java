package com.web.wlsms.service.alarm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.AlarmDao;
import com.web.wlsms.entity.AlarmConfigEntity;
import com.web.wlsms.entity.AlarmDataEntity;
import com.web.wlsms.request.SimpleRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("AlarmService")
public class AlarmService {

	@Resource
	private AlarmDao alarmDao;
	public void insertAlarmConfig(AlarmConfigEntity alarmConfigEntity){
		alarmDao.insertAlarmConfig(alarmConfigEntity);
	}

	public PageInfo getAlarmConfig(SimpleRequest<Integer> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<AlarmConfigEntity> list = alarmDao.getAlarmConfig();
		return new PageInfo<>(list);
	}

	public PageInfo getAlarmInfoList(SimpleRequest<Integer> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<AlarmDataEntity> list = alarmDao.getAlarmInfoList();
		return new PageInfo<>(list);
	}

}
