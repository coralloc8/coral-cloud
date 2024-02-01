package com.coral.cloud.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huss
 * @version 1.0
 * @className MonitorApplication
 * @description 监控服务
 * @date 2023/4/11 11:16
 */
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
