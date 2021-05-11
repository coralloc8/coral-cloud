package com.coral.web.auth.security.token;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.ToString;

/**
 * 自定义access token数据结构，用于后续的自定义序列化
 * 
 * @author huss
 */
@ToString
@JsonSerialize(using = CustomOAuth2AccessTokenJacksonSerializer.class)
public class CustomOAuth2AccessToken extends DefaultOAuth2AccessToken {

    public CustomOAuth2AccessToken(String value) {
        super(value);
    }

    public CustomOAuth2AccessToken(OAuth2AccessToken accessToken) {
        super(accessToken);
    }

}
