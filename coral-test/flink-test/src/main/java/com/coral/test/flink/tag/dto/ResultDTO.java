package com.coral.test.flink.tag.dto;

import com.coral.base.common.CollectionUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultDTO<T> {

    private List<String> errors;

    private List<T> data;

    boolean isSuccess() {
        return CollectionUtil.isNotBlank(getErrors());
    }

    public ResultDTO(List<String> errors, List<T> data) {
        this.errors = errors;
        this.data = data;
    }

    public static <T> ResultDTO<T> create() {
        return new ResultDTO<>(new ArrayList<>(16), new ArrayList<>(16));
    }
}
