package com.coral.test.spring.graphql_jdk8.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页数据
 *
 * @author huss
 * @date 2024/1/3 17:54
 * @packageName com.coral.test.spring.graphql.dto
 * @className PageQueryDTO
 */
@Data
public class PageQueryDTO {

    /**
     *
     */
    @NotNull(message = "每页显示条数不能为空")
    @Min(value = 1, message = "每页显示条数不能小于1")
    @Max(value = 1000, message = "每页显示条数不能大于1000")
    private Long size;

    /**
     *
     */
    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "当前页数不能小于1")
    private Long current;

}
