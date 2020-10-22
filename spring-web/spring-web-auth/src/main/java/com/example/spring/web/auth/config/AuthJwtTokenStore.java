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
     * @formatter:off
     * jks文件自己生成
     * 其实有两种实现方式，一种是采用对称加密的方式，直接converter.setSigningKey("")即可，
     * 在生产环境推荐使用公私匙加密的方式更安全，这种方式生成的access_token长度也会很长，至于jks文件生成方式可以使用
     * (1）生成*.jks
     * keytool -genkeypair -alias oauth2 -keyalg RSA -keypass oauth2 -keystore oauth2.jks -storepass oauth2
     * keytool -importkeystore -srckeystore oauth2.jks -destkeystore oauth2.jks -deststoretype pkcs12
     *（2）生成公钥，复制保存到pubkey.txt
     * keytool -list -rfc --keystore oauth2.jks | openssl x509 -inform pem -pubkey
     * @return
     * @formatter:on
     */
    private KeyPair keyPair() {
        return new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray()).getKeyPair("oauth2");

    }

    /**
     * 自定义令牌声明，添加额外的属性 需要注意的是此处添加的节点其实是和access_token属于同一父节点下的子节点，而不是包含在access_token里面
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
