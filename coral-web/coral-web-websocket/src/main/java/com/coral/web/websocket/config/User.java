package com.coral.web.websocket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * @author huss
 * @version 1.0
 * @className User
 * @description 用户信息
 * @date 2022/10/28 15:01
 */

@AllArgsConstructor
@NoArgsConstructor
public class User implements Principal {

    @Getter
    private String name;

}
