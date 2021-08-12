package com.coral.simple.web1;

import com.coral.simple.web1.config.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description: 启动
 * @author: huss
 * @time: 2020/10/24 14:58
 */
@SpringBootApplication
public class SimpleWebOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWebOneApplication.class, args);
    }


    @Component
    public class MyTest implements ApplicationRunner{
        @Autowired
        private MyProperties myProperties;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println(">>>>pro:"+ myProperties.getName());
        }
    }
}
