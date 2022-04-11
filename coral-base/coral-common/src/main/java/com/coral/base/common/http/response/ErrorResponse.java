package com.coral.base.common.http.response;

import com.coral.base.common.exception.IErrorCodeMessage;
import lombok.Data;
import lombok.NonNull;

/**
 * @author huss
 * @version 1.0
 * @className ErrorResponse
 * @description 错误响应
 * @date 2022/4/2 8:41
 */
@Data
public class ErrorResponse implements IResponse {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    public ErrorResponse(@NonNull IErrorCodeMessage errorCodeMessage) {
        this.errcode = errorCodeMessage.getErrorCode();
        this.errmsg = errorCodeMessage.getErrorMsg();
    }

    public ErrorResponse(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    @Override
    public boolean success() {
        return false;
    }
}
