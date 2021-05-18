package com.coral.simple.web2.controller;

import com.coral.simple.web2.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huss
 * @version 1.0
 * @className TestController
 * @description test 控制器
 * @date 2021/5/14 14:32
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;


    /**
     * 查看所有消息集合
     *
     * @return
     */
    @GetMapping("list")
    public Result list(String name, Integer age) {
        return Result.success(testService.findAll(name, age));
    }

    /**
     * 查看所有消息集合
     *
     * @return
     */
    @GetMapping("list2")
    public Result list2(String name, Integer age) {
        return Result.success(testService.findAll2(name, age));
    }

    /**
     * 查看所有消息集合
     *
     * @return
     */
    @GetMapping("list3")
    public Result list3(String name, Integer age) {
        return Result.success(testService.findAll3(name, age));
    }
}
