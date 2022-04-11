package com.coral.base.common.http.response;

/**
 * @author huss
 * @version 1.0
 * @className IResponse
 * @description 响应结果
 * @date 2022/4/2 8:40
 */
public interface IResponse {
    /**
     * 是否成功
     *
     * @return
     */
    default boolean success() {
        return true;
    }
}
