package com.coral.test.spring.graphql_jdk8.dto;

import com.coral.test.spring.graphql_jdk8.eums.Sex;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 作者查询
 *
 * @author huss
 * @date 2024/1/2 15:43
 * @packageName com.coral.test.spring.graphql.dto
 * @className AuthorQuery
 */
@Data
public class AuthorQueryDTO {

    @NotNull(message = "作者编码不能为空")
    private List<String> authorNos;

    private Sex sex;

    private LocalDate birthdayStart;


    private LocalDate birthdayEnd;

    private String authorName;

}
