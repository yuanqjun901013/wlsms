package com.web.wlsms.service.data;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.DataDao;
import com.web.wlsms.entity.DataEntity;
import com.web.wlsms.request.SimpleRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("DataService")
public class DataService {

	@Resource
	private DataDao dataDao;
	public void insertData(DataEntity dataEntity){
		dataDao.insertData(dataEntity);
	}

	public PageInfo getDataList(SimpleRequest<Integer> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<DataEntity> list = dataDao.getDataList();
		return new PageInfo<>(list);
	}

}
