package org.huangry.ocr.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    public SpringUtil(){
        System.out.println("");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null){
            synchronized (SpringUtil.class){
                SpringUtil.applicationContext = applicationContext;
            }
        }
    }

    //根据name&class获取实例
    public static <T> T  getBean(String name,Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }

    //根据class获取实例
    public static <T> T  getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }


    //private static HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();


}
