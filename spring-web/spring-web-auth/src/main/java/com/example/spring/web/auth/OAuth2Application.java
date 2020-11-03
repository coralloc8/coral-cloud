package com.example.spring.web.auth;

import com.example.spring.web.auth.service.IOauth2Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.spring.enable.log.annotation.EnableLogging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.SpringFactoriesLoader;

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
