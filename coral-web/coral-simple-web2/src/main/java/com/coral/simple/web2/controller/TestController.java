package com.coral.simple.web2.controller;

import com.coral.base.common.exception.SystemException;
import com.coral.simple.web2.dto.TestSaveDTO;
import com.coral.simple.web2.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查看所有消息集合
     *
     * @return
     */
    @GetMapping("list4")
    public Result list4(String name, Integer age) {
        return Result.success(testService.findAll4(name, age));
    }


    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody TestSaveDTO saveDTO) {
        testService.save(saveDTO.getName(), saveDTO.getAge());
        return Result.success();
    }

    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save2")
    public Result save2(@RequestBody TestSaveDTO saveDTO) {
        testService.save2(saveDTO.getName(), saveDTO.getAge());
        return Result.success();
    }
    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save3")
    public Result save3(@RequestBody TestSaveDTO saveDTO) throws SystemException {
        testService.save3(saveDTO.getName(), saveDTO.getAge());
        return Result.success();
    }

    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save4")
    public Result save4(@RequestBody TestSaveDTO saveDTO) {
        testService.save4(saveDTO.getName(), saveDTO.getAge());
        return Result.success();
    }
}
