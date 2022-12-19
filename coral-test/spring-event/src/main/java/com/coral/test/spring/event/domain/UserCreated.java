package com.coral.test.spring.event.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserInfoCreated
 * @description 用户创建
 * @date 2022/12/10 13:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreated {

    private String userId;

    private String username;

    private Integer age;

    private String sex;
}
