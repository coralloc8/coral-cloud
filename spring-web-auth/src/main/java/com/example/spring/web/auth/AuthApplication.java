package com.example.spring.web.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@SpringBootApplication
@Slf4j
public class AuthApplication {

    public static void main(String[] args) {
        log.info("#####AuthApplication start...");

        SpringApplication.run(AuthApplication.class, args);
    }

}
