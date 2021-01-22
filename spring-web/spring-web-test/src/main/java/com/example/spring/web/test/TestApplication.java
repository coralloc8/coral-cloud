package com.example.spring.web.test;

import com.example.spring.enable.workflow.annotation.EnableWorkflow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@SpringBootApplication
@Slf4j
@EnableWorkflow
public class TestApplication {

    public static void main(String[] args) {
        log.info("#####TestApplication start...");

        SpringApplication.run(TestApplication.class, args);
    }

}
