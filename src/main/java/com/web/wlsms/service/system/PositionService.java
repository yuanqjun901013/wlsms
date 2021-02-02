package com.web.wlsms.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.PositionDao;
import com.web.wlsms.entity.PositionEntity;
import com.web.wlsms.request.SimpleRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("PositionService")
public class PositionService {

	@Resource
	private PositionDao positionDao;
	public void insertPosition(PositionEntity positionEntity){
		positionDao.insertPosition(positionEntity);
	}

	public PageInfo getPositionList(SimpleRequest<Integer> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<PositionEntity> list = positionDao.getPositionList();
		return new PageInfo<>(list);
	}

}
