package com.coral.test.groovy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author huss
 * @version 1.0
 * @className TestService
 * @description todo
 * @date 2022/11/26 9:34
 */
@Service
@Slf4j
public class TestService {

    /**
     * 测试
     *
     * @param name
     * @return
     */
    public String test(String name) {
        log.info(">>>>>name：{}", name);
        return name;
    }
}
