package org.huangry.ocr.api;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.huangry.ocr.common.enums.LanguageType;
import org.huangry.ocr.common.form.ResolutionForm;
import org.huangry.ocr.service.TesseractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图像识别，文字识别
 *
 * 训练工具 {@link }
 */
@Controller
@RequestMapping("/ocr")
public class ApiImageAnalysisController {

    private final Logger logger = LoggerFactory.getLogger(TesseractService.class);


    @Autowired
    private TesseractService tesseractService;

    private final String FORMAT_TIMESTAMP_SHORT="yyyyMMddHHmmssSSS";
    private final String LETTER="abcdefghigklmnopqrstuvwxyz";

    /**
     * 图片解析服务
     * @param form
     * @return 返回解析内容
     */
    @PostMapping("/binary")
    @ResponseBody
    public String analysis4binary(ResolutionForm form){
        logger.info("图片OCR识别 调用图片解析服务");

        try {
            File temp = read2Temporary(form);

            form.setLanguage(LanguageType.CHI_SIM.value());

            // 解析文件
            String data = tesseractService.read(temp,form.getLanguage());
            Map<String, String> result = new HashMap();
            result.put("data",data);
            result.put("title",form.getName());

            boolean delete = temp.delete();

            return JSON.toJSONString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过上传文件流生成临时文件
     * @param form
     * @return
     */
    private File read2Temporary(ResolutionForm form) {
        FileOutputStream fos=null;
        InputStream fis=null;
        File temp=null;
        try {
            MultipartFile image = form.getFile();
            fis = image.getInputStream();
            String type = form.getType();

            String tempFileName =
                    FastDateFormat.getInstance(FORMAT_TIMESTAMP_SHORT).format(new Date())
                    .concat(RandomStringUtils.random(10, LETTER));
            temp = new File(tempFileName.concat(type));
            fos = new FileOutputStream(temp);

            byte[] buf = new byte[4096];
            while (fis.read(buf) != -1){
                fos.write(buf,0,buf.length);
            }

            logger.info("待解析文件位置 {}",temp.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if(fos != null ){
                    fos.close();
                }
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return temp;
    }

    /**
     * 获取可识别语言集合
     * @return 返回可识别语言集合
     */
    //@Log(name = "调用获取图片识别语言类别查询")
    @GetMapping("/language")
    @ResponseBody
    public String language(){
        logger.info(" 获取图片识别语言类别");

        try {
            List<Map<String, String>> data = LanguageType.data();
            return JSON.toJSONString(data);
        } catch (Exception e) {
            logger.error("调用获取图片识别语言类别查询 失败 ,",e);
        }
        return null;
    }

}
