package com.coral.cloud.user.common.errormsg;

import org.springframework.http.HttpStatus;

/**
 * @author huss
 * @version 1.0
 * @className UserErrorMessageDesc
 * @description 用户错误码说明
 * @date 2022/4/2 11:11
 */
public interface UserErrorMessageDesc {

    /**
     * 用户不存在
     */
    String USER_NOT_EXIST_CODE = "1001001";
    String USER_NOT_EXIST_DESC = "用户不存在";
    Integer USER_NOT_EXIST_HTTP = HttpStatus.NOT_FOUND.value();
}
