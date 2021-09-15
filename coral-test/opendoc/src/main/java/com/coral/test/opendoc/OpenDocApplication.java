/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.coral.test.opendoc;

import com.coral.test.opendoc.config.openapi.OpenApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


/**
 * @author huss
 */
@SpringBootApplication
@Slf4j
public class OpenDocApplication {

    @Autowired
    private OpenApiConfig openApiConfig;

    public static void main(String[] args) {
        SpringApplication.run(OpenDocApplication.class, args);

    }

    @Component
    public class InitRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) throws Exception {
            log.info(">>>>>init...");
            openApiConfig.printVersion();
        }


    }

}