package com.web.wlsms.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.PositionDao;
import com.web.wlsms.entity.PositionEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("PositionService")
public class PositionService {

	@Resource
	private PositionDao positionDao;
	public BaseResponse savePosition(PositionEntity positionEntity){
		int num = positionDao.insertPosition(positionEntity);
		if(num >0){
			return BaseResponse.ok("新增信息成功");
		}else {
			return BaseResponse.fail("保存失败");
		}
	}

	public PageInfo getPositionList(SimpleRequest<String> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map map = new HashMap();
		List<PositionEntity> list = positionDao.getPositionList(request.getRequest());
		return new PageInfo<>(list);
	}
	public List<PositionEntity> getPositionArr() {
		Map map = new HashMap();
		List<PositionEntity> list = positionDao.getPositionList("");
		return list;
	}

	public BaseResponse updatePosition(PositionEntity positionEntity){
		int num = positionDao.updatePosition(positionEntity);
		if(num >0){
			return BaseResponse.ok("更新信息成功");
		}else {
			return BaseResponse.fail("更新失败");
		}
	}

	public BaseResponse destroyPosition(PositionEntity positionEntity){
		int num = positionDao.destroyPosition(positionEntity);
		if(num >0){
			return BaseResponse.ok("删除信息成功");
		}else {
			return BaseResponse.fail("删除失败");
		}
	}
}
