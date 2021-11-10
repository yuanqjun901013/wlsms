package com.web.wlsms.service.data;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.DataDao;
import com.web.wlsms.entity.*;
import com.web.wlsms.request.DataProCodeRequest;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.request.UpLoadRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.alarm.AlarmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("DataService")
public class DataService {

	@Resource
	private DataDao dataDao;
	@Resource
	private AlarmService alarmService;
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
			if(list.size() > 0){
				for(DataEntity dataEntity:list){
				  ReDataValue reDataValue =	getReValue(dataEntity.getCjTime(),dataEntity.getPositionCode());
				  if(null != reDataValue) {
					  dataEntity.setReSxplValue(reDataValue.getReSxplValue());
					  dataEntity.setReBpqplValue(reDataValue.getReBpqplValue());
					  dataEntity.setReZplValue(reDataValue.getReZplValue());
					  dataEntity.setReXxplValue(reDataValue.getReXxplValue());
					  dataEntity.setReTzslValue(reDataValue.getReTzslValue());
					  dataEntity.setReXxslValue(reDataValue.getReXxslValue());
					  dataEntity.setReXzbValue(reDataValue.getReXzbValue());
					  dataEntity.setReWzlValue(reDataValue.getReWzlValue());
				  }
				}
			}
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

	public int insertBatchData(List<DataEntity> dataEntities) {
		return dataDao.insertBatchData(dataEntities);
	}

	public BaseResponse saveBatch(DataProCodeRequest request){
		Map map = new HashMap();
		map.put("proCodeManual",request.getProCodeManual());
		map.put("proCodeMachine",request.getProCodeMachine());
		//查出人工数据
		List<ManualDataModel> manualDataModels = dataDao.getManualDataList(map);
		//查出机器数据
		List<MachineDataModel> machineDataModels = dataDao.getMachineDataList(map);
		if(null == manualDataModels || manualDataModels.size() == 0){
			return BaseResponse.fail("人工底数为空");
		}
		if(null == machineDataModels || machineDataModels.size() == 0){
			return BaseResponse.fail("机器底数为空");
		}
		if(!manualDataModels.get(0).getPositionCode().equals(machineDataModels.get(0).getPositionCode())){
			return BaseResponse.fail("人工数据和机器数据非同位置数据，请新选择匹配");
		}
		List<DataEntity> dataEntities = new ArrayList<>();
		//处理两条数据
		if(manualDataModels.size() > machineDataModels.size()){
			//数据量有差异1，生成一条告警保存到告警表
			AlarmDataEntity alarmDataEntity = new AlarmDataEntity();
			alarmDataEntity.setAlarmTitle("人工上传底数行数据多于机器底数");
			alarmDataEntity.setAlarmContent("公文批次号:"+ request.getProCodeManual() +
					";人工底数量:"+manualDataModels.size()+"条;公文批次号:"+ request.getProCodeMachine()
					+ ";机器底数量:"+machineDataModels.size()+"条;");
			alarmService.insertAlarmData(alarmDataEntity);
		}
		if(manualDataModels.size() < machineDataModels.size()){
			//数据量有差异2，生成一条告警保存到告警表
			AlarmDataEntity alarmDataEntity = new AlarmDataEntity();
			alarmDataEntity.setAlarmTitle("人工上传底数行数据少于机器底数");
			alarmDataEntity.setAlarmContent("公文批次号:"+ request.getProCodeManual() +
					";人工底数量:"+manualDataModels.size()+"条;公文批次号:"+ request.getProCodeMachine()
					+ ";机器底数量:"+machineDataModels.size()+"条;");
			alarmService.insertAlarmData(alarmDataEntity);
		}
		for(ManualDataModel manualDataModel:manualDataModels){
				DataEntity dataEntity = new DataEntity();
				dataEntity.setSxzfqName(manualDataModel.getSxzfqName());
				dataEntity.setSxplValue(manualDataModel.getSxplValue());
				dataEntity.setBpqplValue(manualDataModel.getBpqplValue());
				dataEntity.setZplValue(manualDataModel.getZplValue());
				dataEntity.setXxplValue(manualDataModel.getXxplValue());
				dataEntity.setSystemName(manualDataModel.getSystemName());
				dataEntity.setTzslValue(manualDataModel.getTzslValue());
				dataEntity.setXxslValue(manualDataModel.getXxslValue());
				dataEntity.setTzfsName(manualDataModel.getTzfsName());
				dataEntity.setXdbmCode(manualDataModel.getXdbmCode());
				dataEntity.setXzbValue(manualDataModel.getXzbValue());
				dataEntity.setCjTime(manualDataModel.getCjTime());
				dataEntity.setWzlValue(manualDataModel.getWzlValue());
				dataEntity.setProCode(request.getProCodeManual());
				dataEntity.setPositionCode(manualDataModel.getPositionCode());
				dataEntities.add(dataEntity);
			}

		//存储归档数据
		int saveDataNum = dataDao.insertBatchData(dataEntities);
		if(saveDataNum >0){
			//更新人工数据和机器数据状态
           dataDao.updateManualState(request.getProCodeManual());
           dataDao.updateMachineState(request.getProCodeMachine());
			return BaseResponse.ok("底数归档成功");
		}else {
			return BaseResponse.fail("底数归档失败");
		}
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

	/**
	 * 删除人工底数
	 * @param manualDataModel
	 * @return
	 */
	public BaseResponse deleteManual(ManualDataModel manualDataModel){
		int num = dataDao.deleteManual(manualDataModel);
		if(num >0){
			return BaseResponse.ok("删除信息成功");
		}else {
			return BaseResponse.fail("删除失败");
		}
	}

	/**
	 * 删除机器底数
	 * @param machineDataModel
	 * @return
	 */
	public BaseResponse deleteMachine(MachineDataModel machineDataModel){
		int num = dataDao.deleteMachine(machineDataModel);
		if(num >0){
			return BaseResponse.ok("删除信息成功");
		}else {
			return BaseResponse.fail("删除失败");
		}
	}

	/**
	 * 删除归档底数
	 * @param dataEntity
	 * @return
	 */
	public BaseResponse deleteData(DataEntity dataEntity){
		int num = dataDao.deleteData(dataEntity);
		if(num >0){
			return BaseResponse.ok("删除信息成功");
		}else {
			return BaseResponse.fail("删除失败");
		}
	}

	/**
	 * 编辑归档底数
	 * @param dataEntity
	 * @return
	 */
	public BaseResponse updateData(DataEntity dataEntity){
		int num = dataDao.updateData(dataEntity);
		if(num >0){
			return BaseResponse.ok("更新数据成功");
		}else {
			return BaseResponse.fail("更新数据失败");
		}
	}

	/**
	 * 系统总览
	 * @return
	 */
	public List<AllParamEntity> cotData(){
		List<AllParamEntity> cotDataList = new ArrayList<>();
		Long userCount = dataDao.userCount();
		AllParamEntity user = getBat("用户数",userCount);
		cotDataList.add(user);
		Long alarmCount = dataDao.alarmCount();
		AllParamEntity alarm = getBat("告警数",alarmCount);
		cotDataList.add(alarm);
		Long roleCount = dataDao.roleCount();
		AllParamEntity role = getBat("角色数",roleCount);
		cotDataList.add(role);
		Long wmdCount = dataDao.wmdCount();
		AllParamEntity wmd = getBat("底数",wmdCount);
		cotDataList.add(wmd);
		Long operationCount = dataDao.operationCount();
		AllParamEntity operation = getBat("运行记录",operationCount);
		cotDataList.add(operation);
		return cotDataList;
	}

	/**
	 * 底数录入情况统计
	 * @return
	 */
	public List<AllParamEntity> vcData(){
		List<AllParamEntity> vcDataList = new ArrayList<>();
		Long manualCount = dataDao.manualCount();
		AllParamEntity manual = getBat("人工底数",manualCount);
		vcDataList.add(manual);
		Long machineCount = dataDao.machineCount();
		AllParamEntity machine = getBat("机器底数",machineCount);
		vcDataList.add(machine);
		Long dataCount = dataDao.dataCount();
		AllParamEntity data = getBat("已融合",dataCount);
		vcDataList.add(data);
		Long dataNoneCount = dataDao.dataNoneCount();
		AllParamEntity dataNone = getBat("未融合",dataNoneCount);
		vcDataList.add(dataNone);
        return vcDataList;
	}

	private AllParamEntity getBat(String name,Long value){
		AllParamEntity allParamEntity = new AllParamEntity();
		allParamEntity.setName(name);
		allParamEntity.setValue(value);
		return allParamEntity;
	}

	//最近七天底数情况折线对比图

	public Map<String, Object> chartData(){
		Map<String, Object> chartData = new HashMap<>();
		//算出最近七天的日期(当前日期往前推7天)
		List<String> dayList = getDays(6);
		chartData.put("dayList",dayList);
		//机器底数
		DataChartModel jqDataChart = new DataChartModel();
		jqDataChart.setName("机器底数");
		jqDataChart.setType("line");
		List<Long> jqData = new ArrayList<>();
		//人工底数
		DataChartModel rgDataChart = new DataChartModel();
		rgDataChart.setName("人工底数");
		rgDataChart.setType("line");
		List<Long> rgData = new ArrayList<>();
		//已融合
		DataChartModel yrDataChart = new DataChartModel();
		yrDataChart.setName("已融合");
		yrDataChart.setType("line");
		List<Long> yrData = new ArrayList<>();
		//未融合
		DataChartModel wrDataChart = new DataChartModel();
		wrDataChart.setName("未融合");
		wrDataChart.setType("line");
		List<Long> wrData = new ArrayList<>();
		for(String date: dayList){
			//jq
			Long jqCount = dataDao.jqCount(date);
			jqData.add(jqCount);
			//rg
			Long rgCount = dataDao.rgCount(date);
			rgData.add(rgCount);
			//yr
			Long yrCount = dataDao.yrCount(date);
			yrData.add(yrCount);
			//wr
			Long wrCount = dataDao.wrCount(date);
			wrData.add(wrCount);
		}
		List<DataChartModel> chartModels = new ArrayList<>();
		jqDataChart.setData(jqData);
		chartModels.add(jqDataChart);
		rgDataChart.setData(rgData);
		chartModels.add(rgDataChart);
		yrDataChart.setData(yrData);
		chartModels.add(yrDataChart);
		wrDataChart.setData(wrData);
		chartModels.add(wrDataChart);
		chartData.put("chartValue",chartModels);
		return chartData;
	}

	private static Date getDateAdd(int days){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		return c.getTime();

	}

	private static List<String> getDays(int days){
		List<String> dayList = new ArrayList<>();
		Calendar start = Calendar.getInstance();
		start.setTime(getDateAdd(days));
		Long startTIme = start.getTimeInMillis();
		Calendar end = Calendar.getInstance();
		end.setTime(new Date());
		Long endTime = end.getTimeInMillis();
		Long oneDay = 1000 * 60 * 60 * 24l;
		Long time = startTIme;
		while (time <= endTime) {
			Date d = new Date(time);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dayList.add(df.format(d));
			time += oneDay;
		}
		return dayList;
	}

	/**
	 * 查询推荐值
	 * @param cjTime
	 * @return
	 */
	public BaseResponse getRecommend(String cjTime){
		Map<String, Object> param = new HashMap<>();
		param.put("cjTime",cjTime);
		param.put("positionCode","");
		ReDataValue reDataValue = dataDao.getRecommend(param);
	   if(null != reDataValue && StringUtils.isNotBlank(reDataValue.getReSxplValue())){
	   	return BaseResponse.ok(reDataValue);
	   }else {
	   	return BaseResponse.fail("查询推荐数据失败");
	   }
	}

	private ReDataValue getReValue(String cjTime,String positionCode){
		Map<String, Object> param = new HashMap<>();
		param.put("cjTime",cjTime);
		param.put("positionCode",positionCode);
		ReDataValue reDataValue = dataDao.getRecommend(param);
		if(null != reDataValue && StringUtils.isNotBlank(reDataValue.getReSxplValue())){
			return reDataValue;
		}else {
			return null;
		}
	}
}
