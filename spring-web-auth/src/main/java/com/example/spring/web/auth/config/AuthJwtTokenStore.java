package com.example.spring.web.auth.config;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.example.spring.web.auth.security.token.CustomJwtAccessTokenConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Configuration
@Slf4j
public class AuthJwtTokenStore {

    /**
     * 存储token
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // return new RedisTokenStore(redisConnectionFactory);//RedisConnectionFactory redisConnectionFactory
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成JWT 中的 OAuth2 令牌
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomJwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * jks文件自己生成
     * 
     * @return
     */
    private KeyPair keyPair() {
        return new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray()).getKeyPair("oauth2");

    }

    /**
     * 自定义令牌声明，添加额外的属性
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (OAuth2AccessToken accessToken, OAuth2Authentication authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>(16);

            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
