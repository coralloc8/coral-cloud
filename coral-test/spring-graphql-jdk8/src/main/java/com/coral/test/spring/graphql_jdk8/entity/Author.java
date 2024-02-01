package com.coral.test.spring.graphql_jdk8.entity;

import com.coral.test.spring.graphql_jdk8.eums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 作者
 *
 * @author huss
 * @date 2023/12/28 13:55
 * @packageName com.coral.test.spring.graphql.model
 * @className Author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author implements Serializable {

    private Long id;

    private String authorNo;

    private String authorName;

    private Sex sex;

    private LocalDate birthday;

    private LocalDateTime createTime;
}
