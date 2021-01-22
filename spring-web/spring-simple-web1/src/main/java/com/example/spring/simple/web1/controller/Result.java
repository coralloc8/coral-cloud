package com.example.spring.simple.web1.controller;

import lombok.Data;

/**
 * @description: TODO
 * @author: huss
 * @time: 2021/1/11 17:47
 */
@Data
public class Result {
    private int code;

    private Object data;

    private Result(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(0, data);
    }

    public static Result faiture(Object data) {
        return new Result(1, data);
    }
}
