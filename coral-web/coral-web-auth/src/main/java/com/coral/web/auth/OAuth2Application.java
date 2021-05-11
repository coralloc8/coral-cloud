package com.coral.web.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coral.enable.log.annotation.EnableLogging;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@EnableLogging
@SpringBootApplication
@Slf4j
public class OAuth2Application {

    public static void main(String[] args) {
        log.info("#####Spring AuthApplication start...");

        SpringApplication.run(OAuth2Application.class, args);
    }

}
