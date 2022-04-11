package com.coral.base.common.http;

import com.coral.base.common.http.response.IResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: http 相应信息
 * @author: huss
 * @time: 2021/3/2 10:16
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpResponseInfo implements IResponse {

    /**
     * http状态码
     */
    private Integer httpStatus;

    /**
     * 返回值
     */
    private String body;

    private Exception error;

    public HttpResponseInfo(Integer httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public HttpResponseInfo(Exception error) {
        this.error = error;
    }

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isOK() {
        return httpStatus != null && httpStatus.equals(200) && error == null;
    }

    @Override
    public boolean success() {
        return isOK();
    }

    public boolean hasError() {
        return error != null;
    }

}
