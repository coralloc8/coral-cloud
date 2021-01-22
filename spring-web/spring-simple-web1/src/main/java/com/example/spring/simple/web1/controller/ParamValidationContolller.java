package com.example.spring.simple.web1.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.*;

import com.example.spring.common.validator.ValidatorErrorMsg;
import com.example.spring.common.validator.ValidatorFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

/**
 * @description: 参数校验
 * @author: huss
 * @time: 2021/1/11 17:46
 */
@RestController
@RequestMapping("/validation")
public class ParamValidationContolller {

    @PostMapping("/test")
    public Result test(@RequestBody @Valid Person person, BindingResult result) {
        if (result.getErrorCount() > 0) {
            return Result.faiture(result.getAllErrors());
        }

        return Result.success(person);
    }

    @PostMapping("/test1")
    public Result test(@RequestBody Person person) {
       
        List<ValidatorErrorMsg> errorMsgs = ValidatorFactory.convert(ValidatorFactory.getValidator().validate(person));
            
        
        if (!errorMsgs.isEmpty()) {
            return Result.faiture(errorMsgs);
        }

        return Result.success(person);
    }

    @Data
    @Valid
    static class Person {

        @NotBlank(message = "名称不能为空")
        private String personName;

        // @NotNull(message = "年龄不能为空")
        @NotNull
        @Max(30)
        @Min(18)
        private Integer age;

        @Valid
        private List<Score> scores;
    }

    @Data
    static class Score {

        @Max(100)
        @Min(0)
        @NotNull
        private Integer score;

        @NotBlank
        private String className;

    }

}
