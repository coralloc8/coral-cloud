package com.coral.cloud.user.common.errormsg;

import com.coral.base.common.exception.IErrorCodeMessage;
import com.coral.base.common.exception.IHttpStatus;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className UserErrorMessage
 * @description 用户异常信息
 * @date 2022/4/2 9:16
 */
public enum UserErrorMessage implements IErrorCodeMessage<UserErrorMessage>, IHttpStatus {

    /**
     * 10 user-server  01 用户管理  001具体错误码索引
     */

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(UserErrorMessageDesc.USER_NOT_EXIST_CODE,
            UserErrorMessageDesc.USER_NOT_EXIST_DESC,
            UserErrorMessageDesc.USER_NOT_EXIST_HTTP
    ),


    ;
    @Getter
    private Integer errorCode;

    @Getter
    private String errorMsg;

    @Getter
    private Integer httpStatus;

    UserErrorMessage(String errorCode, String errorMsg, Integer httpStatus) {
        this.errorCode = Integer.valueOf(errorCode);
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

}
