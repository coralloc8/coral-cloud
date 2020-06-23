package com.example.spring.web.test.config.oauth2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 生成JWT 中的 OAuth2 令牌
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(this.getPubKey());
        return converter;
    }

    private String getPubKey() {
        Resource resource = new ClassPathResource("pubkey.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            // return getKeyFromAuthorizationServer();
            log.error("error:", ioe);
        }
        return "";
    }

    // private String getKeyFromAuthorizationServer() {
    //
    // String pubKey = new RestTemplate().getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class);
    // try {
    // Map map = objectMapper.readValue(pubKey, Map.class);
    // System.out.println("联网公钥");
    // return map.get("value").toString();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }
}
