package com.web.wlsms.service.data;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.DataDao;
import com.web.wlsms.entity.DataEntity;
import com.web.wlsms.entity.MachineDataModel;
import com.web.wlsms.entity.ManualDataModel;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.DataProCodeRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.request.UpLoadRequest;
import com.web.wlsms.response.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public PageInfo getDataList(SimpleRequest<Map> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map<String,Object> param = new HashMap<>();
		try {
			if(StringUtils.isNotBlank(request.getQueryBt())){
				param.put("queryBt",request.getQueryBt());
			}
			if(StringUtils.isNotBlank(request.getStartTime())){
				param.put("startTime",request.getStartTime());
			}
			if(StringUtils.isNotBlank(request.getEndTime())){

				param.put("endTime",request.getEndTime());
			}
		List<DataEntity> list = dataDao.getDataList(param);
		return new PageInfo<>(list);
		}catch (Exception e){
			return new PageInfo();
		}
	}

	public PageInfo getManualDataList(SimpleRequest request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map<String,Object> param = new HashMap<>();
		try {
			if(StringUtils.isNotBlank(request.getQueryBt())){
				param.put("queryBt",request.getQueryBt());
			}
			if(StringUtils.isNotBlank(request.getStartTime())){
				param.put("startTime",request.getStartTime());
			}
			if(StringUtils.isNotBlank(request.getEndTime())){

				param.put("endTime",request.getEndTime());
			}
			List<ManualDataModel> list = dataDao.getManualDataList(param);
			return new PageInfo<>(list);
		}catch (Exception e){
			return new PageInfo();
		}
	}

	public PageInfo getMachineDataList(SimpleRequest<Map> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		Map<String,Object> param = new HashMap<>();
		try {
			if (StringUtils.isNotBlank(request.getQueryBt())) {
				param.put("queryBt", request.getQueryBt());
			}
			if (StringUtils.isNotBlank(request.getStartTime())) {
				param.put("startTime", request.getStartTime());
			}
			if (StringUtils.isNotBlank(request.getEndTime())) {

				param.put("endTime", request.getEndTime());
			}
			List<MachineDataModel> list = dataDao.getMachineDataList(param);
			return new PageInfo<>(list);
		}catch (Exception e){
			return new PageInfo();
		}
	}

	/**
	 * 上传文件
	 * @param upLoadRequest
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public BaseResponse fileUpload(UpLoadRequest upLoadRequest, MultipartFile file) throws Exception {
		if (file == null || file.isEmpty()) {
			return BaseResponse.fail("未选择需上传的资料文件");
		}
		String filePath = new File("manual_file").getAbsolutePath();
		File fileUpload = new File(filePath);
		if (!fileUpload.exists()) {
			fileUpload.mkdirs();
		}
		fileUpload = new File(filePath, file.getOriginalFilename());
		if (fileUpload.exists()) {
			throw new Exception("上传的文件已存在");
		}
		try {
			file.transferTo(fileUpload);
			return BaseResponse.ok("资料上传成功");
		} catch (IOException e) {
			return BaseResponse.fail("上传日志文件到服务器失败，请重试");
		}
	}

	public int insertManualData(List<ManualDataModel> manualDataModels) {
		return dataDao.insertManualData(manualDataModels);
	}

	public int insertMachineData(List<MachineDataModel> machineDataModels){
		return dataDao.insertMachineData(machineDataModels);
	}

	public int saveBatch(DataProCodeRequest request){
		Map map = new HashMap();
		map.put("proCodeManual",request.getProCodeManual());
		map.put("proCodeMachine",request.getProCodeMachine());
		//查出人工数据
		List<ManualDataModel> manualDataModels = dataDao.getManualDataList(map);
		//查出机器数据
		List<MachineDataModel> machineDataModels = dataDao.getMachineDataList(map);
		return 1;
	}

	/**
	 * 查询未核对人工数据
	 * @return
	 */
	public List<ManualDataModel> getManualDit(){
		return dataDao.getManualDit();
	}

	/**
	 * 查询未核对机器数据
	 * @return
	 */
	public List<MachineDataModel> getMachineDit(){
		return dataDao.getMachineDit();
	}

}
