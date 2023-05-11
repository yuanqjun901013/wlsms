package com.web.wlsms.controller.mongodb;

import com.web.wlsms.dao.DemoDao;
import com.web.wlsms.entity.AbcDataCount;
import com.web.wlsms.entity.DemoEntity;
import com.web.wlsms.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Api(tags = {"mongo数据库demo"})
@RestController
@RequestMapping("/mongo/demo")
public class DemoController {

    @Autowired
    private DemoDao demoDao;

    /**
     * mongoDb测试保存集合数据
     * @param request
     * @return
     */
    @ApiOperation("mongoDb测试保存集合数据")
    @PostMapping("saveDemo")
    public BaseResponse saveDemo(HttpServletRequest request){
        try {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(1L);
            demoEntity.setTitle("Spring Boot 中使用 MongoDB");
            demoEntity.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享");
            demoEntity.setBy("souyunku");
            demoEntity.setUrl("http://www.souyunku.com");
            demoDao.saveDemo(demoEntity);
            return BaseResponse.ok("保存成功！");
        } catch (Exception e) {
            return BaseResponse.fail("保存异常！");
        }
    }

    @ApiOperation("mongoDb测试保存对象")
    @PostMapping("saveAbcData")
    public BaseResponse saveAbcData(HttpServletRequest request){
        try {
            AbcDataCount abcDataCount = new AbcDataCount();
            abcDataCount.setId(1L);
            abcDataCount.setSatePlatName("测试1");
            abcDataCount.setFreq("测试1");
            abcDataCount.setSNR("测试1");
            abcDataCount.setMulAdrmode("测试1");
            abcDataCount.setCarraerType("测试1");
            abcDataCount.setModulateRate("测试1");
            abcDataCount.setFrameLen("测试1");
            abcDataCount.setCodeMode("测试1");
            abcDataCount.setCodeRate("测试1");
            abcDataCount.setSignalType("测试1");
            abcDataCount.setXINXIFENZU("测试1");
            abcDataCount.setIsRaoMa("测试1");
            abcDataCount.setAppearTime("测试1");
            abcDataCount.setDisAppearTime("测试1");
            demoDao.saveAbcData(abcDataCount);
            return BaseResponse.ok("保存成功！");
        } catch (Exception e) {
            return BaseResponse.fail("保存异常！");
        }
    }

    /**
     * mongoDb测试修改集合数据
     * @param request
     * @return
     */
    @ApiOperation("mongoDb测试修改集合数据")
    @PostMapping("updateDemo")
    public BaseResponse updateDemo(HttpServletRequest request){
        try {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setId(1L);
            demoEntity.setTitle("Spring Boot 中使用 MongoDB 并做了修改功能");
            demoEntity.setDescription("关注公众号，搜云库，专注于开发技术的研究与知识分享并修改数据");
            demoEntity.setBy("dayede");
            demoEntity.setUrl("http://www.dayede.com");
            demoDao.updateDemo(demoEntity);
            return BaseResponse.ok("更新成功！");
        } catch (Exception e) {
            return BaseResponse.fail("更新异常！");
        }
    }

    /**
     * mongoDb查询集合数据
     * @param request
     * @return
     */
    @ApiOperation("mongoDb查询集合数据")
    @PostMapping("queryDemo")
    public BaseResponse queryDemo(HttpServletRequest request, Long id){
        try {
            List<Map> obList = demoDao.findAllObject("demo");
            DemoEntity demoEntity = demoDao.findDemoById(id);
            return BaseResponse.ok(obList);
        } catch (Exception e) {
            return BaseResponse.fail("查询异常！");
        }
    }

}
