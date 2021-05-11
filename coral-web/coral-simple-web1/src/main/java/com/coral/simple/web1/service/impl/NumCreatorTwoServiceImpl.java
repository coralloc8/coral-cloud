package com.coral.simple.web1.service.impl;

import com.coral.simple.web1.service.NumCreatorService;
import org.springframework.stereotype.Service;

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
