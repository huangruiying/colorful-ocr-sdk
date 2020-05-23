package org.huangry.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * start
 * @ClassName Application
 * @Author huangruiying
 * @Date 2020/5/2
 * @Version 1.0
 */
@SpringBootConfiguration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);

    }
}
