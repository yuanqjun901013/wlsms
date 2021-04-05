package com.web.wlsms.utils;

import com.web.wlsms.request.ExcelReadResult;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 导出Execel
     *
     * @param request
     * @param response
     * @param templateFileName
     * @param destFileName
     * @param data
     */
    public static void export(HttpServletRequest request,
                              HttpServletResponse response, String templateFileName,
                              String destFileName, Object data) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("data", data);
        XLSTransformer transformer = new XLSTransformer();
        InputStream in = null;
        OutputStream out = null;
        // 设置响应
        try {
            // 使用各浏览器对应编码解决浏览器文件名乱码
            String userAgent = request.getHeader("USER-AGENT");
            if (userAgent.indexOf("MSIE") >= 0) {
                // IE浏览器
                destFileName = URLEncoder.encode(destFileName, "UTF8");
            } else if (userAgent.indexOf("Mozilla") >= 0) {
                // google,火狐浏览器
                destFileName = URLEncoder.encode(destFileName, "UTF8");
            } else {
                // 其他浏览器
                destFileName = URLEncoder.encode(destFileName, "UTF8");
            }
            response.setHeader("Content-disposition",
                    String.format("attachment; filename=\"%s\"", destFileName));
            response.setContentType("application/octet-stream");

            in = ExcelUtil.class.getClassLoader().getResourceAsStream(
                    "templates/excel/export/" + templateFileName);
            Workbook workbook = transformer.transformXLS(in, beans);
            out = response.getOutputStream();
            // 将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException e) {
            logger.error("export error,", e);
        } catch (IOException e) {
            logger.error("export error,", e);
        } catch (Exception e) {
            logger.error("export error,", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("export error,", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("export error,", e);
                }
            }
        }
    }


    /**
     * 读取Excel表格数据
     *
     * @param templateFileName
     * @param inputXLS
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ExcelReadResult<T> readList(String templateFileName, InputStream inputXLS, Class<T> clazz) {
        ExcelReadResult<T> result = new ExcelReadResult<T>();
        result.setStatus(false);
        result.setResult(new ArrayList<T>());
        InputStream inputXML = null;
        try {
            inputXML = ExcelUtil.class.getClassLoader().getResourceAsStream("templates/" + templateFileName);
            XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
            Map<String, Object> beans = new HashMap<String, Object>();
            List<T> list = new ArrayList<T>();
            beans.put("list", list);
            XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
            if (readStatus.isStatusOK()) {
                result.setStatus(true);
                result.setResult(list);
            }
        } catch (Exception e) {
            logger.error("readList error,", e);
        } finally {
            if (inputXML != null) {
                try {
                    inputXML.close();
                } catch (IOException e) {
                    logger.error("readList error", e);
                }
            }
        }
        return result;
    }
}