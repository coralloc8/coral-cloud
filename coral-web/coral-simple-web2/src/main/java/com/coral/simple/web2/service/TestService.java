package com.coral.simple.web2.service;


import com.coral.base.common.mybatis.service.IMybatisService;
import com.coral.database.test.mybatis.primary.entity.Test;
import com.coral.database.test.mybatis.secondary.entity.SecTest;

import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className ITestService
 * @description test
 * @date 2021/5/13 10:34
 */
public interface TestService extends IMybatisService<Test> {

    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    Map<String, Object> findAll3(String name, Integer age);

    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    List<SecTest> findAll2(String name, Integer age);

    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    List<Test> findAll(String name, Integer age);
}
