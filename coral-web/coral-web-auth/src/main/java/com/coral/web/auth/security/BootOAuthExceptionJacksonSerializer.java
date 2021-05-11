package com.coral.web.auth.security;

import java.io.IOException;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义授权服务认证时返回的异常信息序列化
 *
 * @author huss
 */
@Slf4j public class BootOAuthExceptionJacksonSerializer extends StdSerializer<BootOAuth2Exception> {

    protected BootOAuthExceptionJacksonSerializer() {
        super(BootOAuth2Exception.class);
    }

    @Override
    public void serialize(BootOAuth2Exception value, JsonGenerator jgen, SerializerProvider serializerProvider)
        throws IOException {
        Result result = new Results().failure(value.getErrorMessage());
        log.debug(">>>>>BootOAuthExceptionJacksonSerializer serialize:{}", result);
        jgen.writeObject(result);
    }

}