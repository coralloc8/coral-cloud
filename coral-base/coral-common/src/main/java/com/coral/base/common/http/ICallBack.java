package com.coral.base.common.http;

import java.io.IOException;

/**
 * @description: 回调
 * @author: huss
 * @time: 2021/3/1 18:34
 */
public interface ICallBack {

    /**
     * 成功
     * 
     * @param httpResponseInfo
     */
    void onSuccessful(HttpResponseInfo httpResponseInfo);

    /**
     * 失败
     * 
     * @param e
     */
    void onFailure(IOException e);
}
