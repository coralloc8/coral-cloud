package com.coral.simple.web1.service;

import com.coral.base.common.jpa.service.IJpaBaseService;
import com.coral.database.test.jpa.entity.Test;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className ITestService
 * @description test
 * @date 2021/5/13 10:34
 */
public interface TestService extends IJpaBaseService<Test, Long> {

    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    List<Test> findAll(String name, Integer age);
}
