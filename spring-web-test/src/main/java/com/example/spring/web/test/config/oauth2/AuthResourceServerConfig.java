package com.example.spring.web.test.config.oauth2;

import com.example.spring.service.file.config.UploadProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 资源服务
 * 
 * @author huss
 */
@Configuration
@EnableResourceServer
public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

    @Autowired
    private CustomAuthExceptionHandler customAuthExceptionHandler;
    @Autowired
    private UploadProperty uploadProperty;

    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenAccess;

    /**
     * 这里设置需要token验证的url 这些url可以在WebSecurityConfigurerAdapter中排查掉， 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行 WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 匹配所有url
        http.requestMatchers().antMatchers("/**")
            //
            .and().authorizeRequests().antMatchers("/**/druid/**", uploadProperty.getVirtualPath() + "**").permitAll()
            // 所有url都需要授权访问
            .and().authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(customAuthExceptionHandler).accessDeniedHandler(customAuthExceptionHandler);
    }

    @Primary
    @Bean
    public ResourceServerTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(checkTokenAccess);
        tokenService.setClientId(authorizationCodeResourceDetails.getClientId());
        tokenService.setClientSecret(authorizationCodeResourceDetails.getClientSecret());
        return tokenService;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public AuthorizationCodeResourceDetails authorizationCodeResourceDetails() {
        return new AuthorizationCodeResourceDetails();
    }

}