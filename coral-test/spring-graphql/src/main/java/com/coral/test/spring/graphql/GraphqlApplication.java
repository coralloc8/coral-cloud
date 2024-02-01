package com.coral.test.spring.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * graphql 启动类
 *
 * @author huss
 * @date 2023/12/28 13:43
 * @packageName com.coral.test.spring.graphql
 * @className GraphqlApplication
 */
@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }


    @RestController
    @RequestMapping("/test")
    public static class TestController {
        @GetMapping
        public ResponseEntity<String> hello() {
            return ResponseEntity.ok("hello");
        }
    }

}
