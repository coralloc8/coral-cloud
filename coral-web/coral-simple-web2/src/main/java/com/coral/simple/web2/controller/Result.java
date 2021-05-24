package com.coral.simple.web2.controller;

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

    public static Result success() {
        return new Result(0, null);
    }


    public static Result failure(Object data) {
        return new Result(1, data);
    }
}
