package com.example.spring.web.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.example.spring.web.auth.security.handler.CustomAuthExceptionHandler;

/**
 * 同一台服务器上的资源服务
 *
 * @author huss
 */
@Configuration
@EnableResourceServer
public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private CustomAuthExceptionHandler customAuthExceptionHandler;

    /**
     * 这里设置需要token验证的url 这些url可以在WebSecurityConfigurerAdapter中排查掉， 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行 WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //
        http.requestMatchers().antMatchers("/api/**", "/user/**", "/oauth/**")
            //
            .and().authorizeRequests().antMatchers("/api/**", "/user/**").authenticated()
            //
            .and().authorizeRequests().antMatchers("/oauth/**").permitAll();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(customAuthExceptionHandler).accessDeniedHandler(customAuthExceptionHandler);
    }

    /**
     * 创建一个默认的资源服务token
     *
     * @return
     */
    @Primary
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter, tokenEnhancer));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        defaultTokenServices.setTokenStore(tokenStore);
        return defaultTokenServices;
    }
}