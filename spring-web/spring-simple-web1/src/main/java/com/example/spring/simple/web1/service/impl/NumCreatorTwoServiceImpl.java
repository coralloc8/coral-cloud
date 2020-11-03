package com.example.spring.simple.web1.service.impl;

import org.springframework.stereotype.Service;

import com.example.spring.simple.web1.service.NumCreatorService;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: two
 * @author: huss
 * @time: 2020/10/24 15:03
 */
@Slf4j
@Service
public class NumCreatorTwoServiceImpl implements NumCreatorService<Long> {

    @Override
    public Long create() {
        log.info(">>>>>[NumCreatorTwoServiceImpl]开始生成ID...");
        return System.currentTimeMillis();
    }
}
