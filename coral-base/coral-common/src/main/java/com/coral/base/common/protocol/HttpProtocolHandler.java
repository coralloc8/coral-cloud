package com.coral.base.common.protocol;


import com.coral.base.common.enums.ProtocolType;
import com.coral.base.common.http.HttpRequestInfo;
import com.coral.base.common.http.HttpResponseInfo;
import com.coral.base.common.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 * @version 1.0
 * @className HttpProtocolHandler
 * @description http实现
 * @date 2021/4/27 9:27
 */
@Slf4j
public class HttpProtocolHandler implements IProtocolHandler<HttpRequestInfo> {

    @Override
    public ProtocolType getProtocol() {
        return ProtocolType.HTTP;
    }

    @Override
    public String send(HttpRequestInfo httpRequestInfo) throws RuntimeException {
        HttpResponseInfo httpResponseInfo = HttpUtil.getInstance().request(httpRequestInfo);
        if (httpResponseInfo.isOK()) {
            return httpResponseInfo.getBody();
        }
        throw new RuntimeException(httpResponseInfo.getHttpStatus()+"");
    }





}
