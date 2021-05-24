package com.coral.simple.web1.service;


import com.coral.base.common.exception.SystemException;
import com.coral.base.common.jpa.service.IJpaBaseService;
import com.coral.database.test.jpa.primary.entity.Test;
import com.coral.database.test.jpa.secondary.entity.SecTest;

import java.util.List;
import java.util.Map;

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

    /**
     * 保存
     *
     * @param name
     * @param age
     */
    void save(String name, Integer age);

    /**
     * 保存
     *
     * @param name
     * @param age
     */
    void save2(String name, Integer age);

    /**
     * 保存
     *
     * @param name
     * @param age
     */
    void save3(String name, Integer age) throws SystemException;

    /**
     * 保存
     *
     * @param name
     * @param age
     */
    void save4(String name, Integer age);
}
