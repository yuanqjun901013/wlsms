package com.web.wlsms.controller.doc;


import com.github.pagehelper.PageInfo;
import com.web.wlsms.entity.DocManagerEntity;
import com.web.wlsms.request.SimpleRequest;
import com.web.wlsms.request.UpLoadRequest;
import com.web.wlsms.response.BaseResponse;
import com.web.wlsms.service.doc.DocManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Api(tags = {"资源管理"})
@RestController
@RequestMapping("/doc/doc")
public class DocController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocController.class);

    @Resource
    private DocManagerService docManagerService;

    /**
     * 查询资料资源
     *
     * @param params
     * @return
     */
    @ApiOperation("查询资料资源")
    @PostMapping("/docManager")
    public Map<String,Object> docManager(SimpleRequest params) {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            PageInfo getDocList =  docManagerService.getDocList(params);
            resultMap.put("total", getDocList.getTotal());
            resultMap.put("rows", getDocList.getList());
        } catch (Exception e) {
            LOGGER.error("DocController&&docManager is error", e);
            resultMap.put("total", 0);
            resultMap.put("rows", "");
        }
        return resultMap;
    }
    /**
     * 新增资料资源
     */
    @ApiOperation("新增资料资源")
    @PostMapping("fileUpload")
    @ResponseBody
    public BaseResponse fileUpload(HttpServletRequest request, UpLoadRequest upLoadRequest, MultipartFile file){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == upLoadRequest){
            return BaseResponse.fail("参数缺失，请重试");
        }
        if(StringUtils.isBlank(upLoadRequest.getDocName())){
            return BaseResponse.fail("资料名不能为空");
        }
        if(StringUtils.isNotBlank(userNo)){
            upLoadRequest.setUserNo(userNo);
        }
        if(StringUtils.isNotBlank(file.getOriginalFilename())){
            upLoadRequest.setFileName(file.getOriginalFilename());
        }
        try {
          return docManagerService.fileUpload(upLoadRequest,file);
        }catch (Exception e){
          return BaseResponse.fail("文件上传异常");
        }
    }

    /**
     * 下载资料资源
     */
    @ApiOperation("下载资料资源")
    @GetMapping(value = "/fileDownload/{name}")
    @ResponseBody
    public void fileDownload(@PathVariable String name, HttpServletRequest request, HttpServletResponse response)throws Exception{
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        docManagerService.fileDownload(userNo,name,response);
    }

    /**
     * 删除资料资源
     */
    @ApiOperation("删除资料资源")
    @PostMapping("deleteDoc")
    @ResponseBody
    public BaseResponse deleteDoc(HttpServletRequest request, DocManagerEntity docManagerEntity){
        HttpSession session = request.getSession(true);
        String userNo = (String) session.getAttribute("userNo");
        if(null == docManagerEntity){
            return BaseResponse.fail("参数缺失，请重试");
        }
        DocManagerEntity docInfo = docManagerService.getDocInfo(docManagerEntity.getId());
        if(null == docInfo){
            return BaseResponse.fail("数据不存在");
        }
        if(StringUtils.isNotBlank(userNo)){
            if(!userNo.equals(docInfo.getUserNo())){
                return BaseResponse.fail("您无权限删除该资料！请联系分享人删除");
            }
        }
        try {
            docManagerEntity.setDocName(docInfo.getDocName());
            docManagerEntity.setFileName(docInfo.getFileName());
            docManagerEntity.setFilePath(docInfo.getFilePath());
            docManagerEntity.setUserNo(userNo);
            return docManagerService.deleteDoc(docManagerEntity);
        }catch (Exception e){
            return BaseResponse.fail("文件删除异常");
        }
    }
}
