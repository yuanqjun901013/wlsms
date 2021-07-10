package com.web.wlsms.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.ParamDao;
import com.web.wlsms.entity.ParamEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.response.BaseResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("ParamService")
public class ParamService {

	@Resource
	private ParamDao paramDao;
	public void insertParam(ParamEntity paramEntity){
		paramDao.insertParam(paramEntity);
	}

	public PageInfo getParametersList(SimpleRequest<String> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		List<ParamEntity> list = paramDao.getParametersList(request.getRequest());
		return new PageInfo<>(list);
	}


	public BaseResponse paramSubmit(ParamEntity paramEntity){
		int num = paramDao.paramSubmit(paramEntity);
		if(num > 0){
			return BaseResponse.ok("修改成功");
		}else {
			return BaseResponse.fail("修改失败");
		}
	}


}
