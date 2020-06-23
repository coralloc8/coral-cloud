package com.example.spring.web.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@SpringBootApplication
@Slf4j
public class TestApplication {

    public static void main(String[] args) {
        log.info("#####TestApplication start...");

        SpringApplication.run(TestApplication.class, args);
    }

}
