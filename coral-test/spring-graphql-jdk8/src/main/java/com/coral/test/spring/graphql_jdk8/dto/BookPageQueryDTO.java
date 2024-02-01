package com.coral.test.spring.graphql_jdk8.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 作者查询
 *
 * @author huss
 * @date 2024/1/2 15:43
 * @packageName com.coral.test.spring.graphql.dto
 * @className AuthorQuery
 */
@Data
public class BookPageQueryDTO {

    @NotNull(message = "分页数据不能为空")
    @Valid
    private PageQueryDTO pageQuery;

    private String bookName;

    private String authorName;

    private Double priceStart;

    private Double priceEnd;

    private Long chapterStart;

    private Long chapterEnd;
}
