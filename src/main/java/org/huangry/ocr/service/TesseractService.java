package org.huangry.ocr.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.huangry.ocr.common.util.ResourcesUtil;
import org.huangry.ocr.service.server.TesseractServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * ClassName: TesseractService
 * Tesseract 调用服务
 * @author huangruiying
 * @version 1.0
 */
@Service
public class TesseractService {

    private final Logger logger = LoggerFactory.getLogger(TesseractService.class);

    @Autowired
    private TesseractServer tesseractServer;

    // 要识别的语言,字库文件名,可以替换成动态选择
    String language = "eng";
    // 字库文件位置
    String language_file_path = "tessdata/eng.traineddata";

    //读取图片
    public String read(File imageFile,String language) throws IOException {

        ITesseract instance = tesseractServer.get();
        String folder = getTraineddataFolder(language_file_path,language);

        logger.info("数据文件 traineddata 所在文件夹  : {}",folder);
        //tessdata文件夹的目录
        instance.setDatapath(folder);
        instance.setLanguage(language);
        try{
            return instance.doOCR(imageFile);
        }catch(TesseractException e){
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 返回traineddata文件的父级目录(文件夹)
     * @param fileName
     * @return
     */
    private String getTraineddataFolder(String fileName,String language) {
        File file = ResourcesUtil.getFileFromProject(fileName);
        String path = file.getAbsolutePath();

        String system_name = System.getProperty("os.name");
        if(system_name.toLowerCase().contains("windows")){
            return path.replaceAll("\\\\".concat(language).concat(".traineddata"), "");
        }else{
            return path.replaceAll("/".concat(language).concat(".traineddata"), "");
        }
    }
}
