package com.web.wlsms.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.ParamDao;
import com.web.wlsms.entity.ParamEntity;
import com.web.wlsms.request.SimpleRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ParamService")
public class ParamService {

	@Resource
	private ParamDao paramDao;
	public void insertParam(ParamEntity paramEntity){
		paramDao.insertParam(paramEntity);
	}

	public PageInfo getParametersList(SimpleRequest<Integer> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<ParamEntity> list = paramDao.getParametersList();
		return new PageInfo<>(list);
	}

}
