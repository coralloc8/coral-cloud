package com.coral.test.spring.graphql_jdk8.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 *
 * @author huss
 * @date 2024/1/5 13:25
 * @packageName com.coral.test.spring.graphql.vo
 * @className UserInfoVO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private String account;

    private String username;

    private String role;

}
