package org.huangry.ocr.service.server;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.huangry.ocr.common.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Tesseract 服务
 * @author Ryan
 * @version 1.0
 */
@Component
@Order(100)
public class TesseractServer {

    private final static Logger logger = LoggerFactory.getLogger(TesseractServer.class);

    // 初始化服务数量
    private int INIT_CREATE_NUMBER = 10;

    private final ConcurrentLinkedQueue<ITesseract> server = new ConcurrentLinkedQueue();

    //private static CountDownLatch latch = new CountDownLatch(1);

    public TesseractServer(){

        /* @Component 早于 setApplicationContext
        if(null !=  SpringUtil.getBean(TesseractServer.class)){
            throw new RuntimeException("禁止重复注册");
        }*/

        logger.info("init tesseract engine : begin");

        for(int i=0;i<INIT_CREATE_NUMBER;i++){
            this.server.offer(new Tesseract());
        }

        logger.info("init tesseract engine : success");
    }

    // 归还服务，归还失败后未处理 -- 可以归还失败后失败的对象放入某队列fock线程异步归还
    public void put(ITesseract iTesseract){
        this.server.offer(iTesseract);
    }

    // 在队列中获取一个对象
    public ITesseract get(){
        ITesseract poll = this.server.poll();
        if(poll != null){
            return poll;
        }
        return null;
    }

    // 获取当前队列长度
    public int size(){
        return server.size();
    }

}
