package com.coral.test.spring.graphql_jdk8.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 书籍
 *
 * @
 * huss
 * @date 2023/12/28 13:50
 * @packageName com.coral.test.spring.graphql.model
 * @className Employee
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {

    private Long id;

    private String bookName;

    private String authorNo;

    private String authorName;

    private Double price;

    /**
     * 章节
     */
    private Long chapter;

    private LocalDateTime createTime;
}
