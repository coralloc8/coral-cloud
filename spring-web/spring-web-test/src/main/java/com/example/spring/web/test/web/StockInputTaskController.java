package com.example.spring.web.test.web;

import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;
import com.example.spring.web.core.web.BaseController;
import com.example.spring.web.test.workflow.StockInputTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.extern.slf4j.Slf4j;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/12/30 17:28
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class StockInputTaskController extends BaseController {

    @Autowired
    private StockInputTaskService stockInputTaskService;




//    @PostMapping("/deploy")
//    public Result<Void> deploy(){
//        stockInputTaskService.deploy();
//        return this.success();
//    }

    @PostMapping("/submit")
    public Result submit() {
        stockInputTaskService.submit();
        return new Results().success();
    }

    @PostMapping("/groupLeaderApproval")
    public Result groupLeaderApproval() {
        stockInputTaskService.groupLeaderApproval();
        return new Results().success();
    }

}
