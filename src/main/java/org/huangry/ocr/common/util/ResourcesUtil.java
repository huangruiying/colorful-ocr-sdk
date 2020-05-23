package org.huangry.ocr.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 资源文件工具类
 *
 * @author huangruiying
 */
public class ResourcesUtil {

    private final static Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);

    private final static ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    // 初始化文件位置
    private static final String[] SERVLET_RESOURCE_LOCATIONS = {"/"};
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};
    // TODO jar 文件
    // 静态资源位置
    // [/META-INF/resources/, /resources/, /static/, /public/] + /
    private static final String[] RESOURCE_LOCATIONS;
    static {
        RESOURCE_LOCATIONS = new String[SERVLET_RESOURCE_LOCATIONS.length + CLASSPATH_RESOURCE_LOCATIONS.length ];
        System.arraycopy(SERVLET_RESOURCE_LOCATIONS, 0, RESOURCE_LOCATIONS, 0, SERVLET_RESOURCE_LOCATIONS.length);
        System.arraycopy(CLASSPATH_RESOURCE_LOCATIONS, 0, RESOURCE_LOCATIONS, SERVLET_RESOURCE_LOCATIONS.length, CLASSPATH_RESOURCE_LOCATIONS.length);
    }




    /**
     * 在项目内寻找文件
     * 获取resources文件夹内的文件
     * @param folderName 待查找文件，可以是文件，可以是文件夹+文件
     * @return
     */
    public static File getFileFromProject(String folderName) {
        logger.info("获取文件{}",folderName);
        for (String location : getStaticLocations(folderName)) {
            Resource resource = ResourcesUtil.RESOURCE_LOADER.getResource(location);
            try {
                if (resource.exists()) {
                    resource.getURL();
                    return resource.getFile();
                }
            } catch (Exception ex) {
                // ignore
            }
        }
        // 在操作系统内获取文件
        // return new File(folderName);
        throw new RuntimeException(new FileNotFoundException("not found file"));
    }



    /**
     * 获取静态资源路径
     * 根目录为:{@link ResourcesUtil#RESOURCE_LOCATIONS}
     * @param target 文件位置
     * @return
     */
    private static String[] getStaticLocations(String target) {
        String[] result = new String[ResourcesUtil.RESOURCE_LOCATIONS.length];
        for (int i = 0; i < result.length; i++) {
            String location = ResourcesUtil.RESOURCE_LOCATIONS[i];
            if (!location.endsWith("/")) {
                location = location + "/";
            }
            result[i] = location + target;
        }
        return result;
    }

}
