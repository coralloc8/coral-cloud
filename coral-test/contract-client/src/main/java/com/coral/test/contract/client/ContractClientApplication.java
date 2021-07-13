package com.coral.test.contract.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.contract.stubrunner.server.EnableStubRunnerServer;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className ClientApplication
 * @description todo
 * @date 2021/7/7 13:40
 */
@AutoConfigureStubRunner
@EnableStubRunnerServer
@SpringBootApplication
public class ContractClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContractClientApplication.class, args);
        String str = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher("中国123abc");
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(">>" + group);
        }
    }
}