package com.coral.cloud.user;

import com.coral.cloud.user.common.config.openapi.OpenApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className UserApplication
 * @description user启动项
 * @date 2022/3/31 17:36
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

    @Autowired
    private OpenApiConfig openApiConfig;

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Component
    public class InitRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            log.info(">>>>>init...");
            openApiConfig.printVersion();
        }
    }
}
