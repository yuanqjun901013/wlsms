package com.web.wlsms.service.doc;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.wlsms.dao.DocManagerDao;
import com.web.wlsms.entity.DocManagerEntity;
import com.web.wlsms.entity.MessageEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.request.UpLoadRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.system.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@Service("DocManagerService")
public class DocManagerService {

	@Resource
	private DocManagerDao docManagerDao;

	@Autowired
	MessageService messageService;

	/**
	 * 加载资源列表
	 * @param request
	 * @return
	 */
	public PageInfo getDocList(SimpleRequest<String> request) {
		PageHelper.startPage(request.getPage(), request.getRows());
		List<DocManagerEntity> list = docManagerDao.getDocList(request.getRequest());
		return new PageInfo<>(list);
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
		String filePath = new File("doc_file").getAbsolutePath();
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
			upLoadRequest.setFilePath(filePath);
			//插入数据库
            docManagerDao.insertDoc(upLoadRequest);
			//上传资料信息
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setUserNo(upLoadRequest.getUserNo());
			messageEntity.setTitle("分享上传资料");
			messageEntity.setContent(upLoadRequest.getUserNo()+":分享了"+upLoadRequest.getFileName());
			messageService.insertMessage(messageEntity);
			return BaseResponse.ok("资料上传成功");
		} catch (IOException e) {
			return BaseResponse.fail("上传日志文件到服务器失败，请重试");
		}
	}

	/**
	 * 下载资料
	 * @param name
	 * @param response
	 * @throws Exception
	 */
	public void fileDownload(String userNo, String name, HttpServletResponse response) throws Exception {
		File file = new File("doc_file" + File.separator + name);

		if (!file.exists()) {
			throw new Exception(name + "文件不存在");
		}
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment;fileName=" + name);

		byte[] buffer = new byte[1024];
		try (FileInputStream fis = new FileInputStream(file);
			 BufferedInputStream bis = new BufferedInputStream(fis)) {

			OutputStream os = response.getOutputStream();

			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
			//上传资料信息
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setUserNo(userNo);
			messageEntity.setTitle("下载资料");
			messageEntity.setContent(userNo+":下载了"+name);
			messageService.insertMessage(messageEntity);
		}
	}

	/**
	 * 删除资料
	 * @param docManagerEntity
	 * @return
	 */
	public BaseResponse deleteDoc(DocManagerEntity docManagerEntity){
		int num = docManagerDao.deleteDoc(docManagerEntity);
		if(num > 0){
			//删除文件下文件
			//定义文件路径
			String filePath = docManagerEntity.getFilePath()+"/"+docManagerEntity.getFileName();
			//这里因为我文件是相对路径 所以需要在路径前面加一个点
			File file = new File(filePath);
			if (file.exists()){//文件是否存在
				file.delete();//删除文件
			}
			return BaseResponse.ok("删除成功");
		}else{
			return BaseResponse.fail("删除失败");
		}

	}

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public DocManagerEntity getDocInfo(long id){
		return docManagerDao.getDocInfoById(id);
	}
}
