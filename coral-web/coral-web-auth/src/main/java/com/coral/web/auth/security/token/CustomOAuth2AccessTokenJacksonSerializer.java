package com.coral.web.auth.security.token;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.Assert;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义序列化
 * 
 * @author huss
 */
@Slf4j
public class CustomOAuth2AccessTokenJacksonSerializer extends StdSerializer<CustomOAuth2AccessToken> {

    protected CustomOAuth2AccessTokenJacksonSerializer() {
        super(CustomOAuth2AccessToken.class);
    }

    @Override
    public void serialize(CustomOAuth2AccessToken token, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException {

        Map<String, Object> map = new HashMap<>(16);
        map.put(OAuth2AccessToken.ACCESS_TOKEN, token.getValue());
        map.put(OAuth2AccessToken.TOKEN_TYPE, token.getTokenType());
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            map.put(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
        }
        Date expiration = token.getExpiration();
        if (expiration != null) {
            long now = System.currentTimeMillis();
            map.put(OAuth2AccessToken.EXPIRES_IN, (expiration.getTime() - now) / 1000);
        }
        Set<String> scope = token.getScope();
        if (scope != null && !scope.isEmpty()) {
            StringBuffer scopes = new StringBuffer();
            for (String s : scope) {
                Assert.hasLength(s, "Scopes cannot be null or empty. Got " + scope + "");
                scopes.append(s);
                scopes.append(" ");
            }
            map.put(OAuth2AccessToken.SCOPE, scopes.substring(0, scopes.length() - 1));
        }
        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            map.put(key, additionalInformation.get(key));
        }

        Result result = new Results().success(map);
        jgen.writeObject(result);

    }

}