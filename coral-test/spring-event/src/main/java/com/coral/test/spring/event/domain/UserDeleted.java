package com.coral.test.spring.event.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className UserInfoChanged
 * @description 用户信息改变
 * @date 2022/12/10 13:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleted {

    private String userId;

}
