package com.coral.web.auth.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.coral.web.auth.security.handler.CustomAuthExceptionHandler;
import com.coral.web.auth.service.IOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.coral.base.common.jpa.enums.GlobalEnabledEnum;
import com.coral.database.test.jpa.primary.entity.OauthClientDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * Security 认证服务
 *
 * @author huss
 */
@EnableAuthorizationServer
@Configuration
@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * @formatter:off
     * grant_type种类
     *
     * 1、authorization_code — 授权码模式(即先登录获取code,再获取token)
     *
     * 2、password — 密码模式(将用户名,密码传过去,直接获取token)
     *
     * 3、client_credentials — 客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向’服务端’获取资源)
     *
     * 4、implicit — 简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)
     *
     * 5、refresh_token — 刷新access_token
     * @formatter:on
     *
     * /oauth/authorize：授权端点
     *      * /oauth/token：令牌端点
     *      * /oauth/confirm_access：用户确认授权提交端点
     *      * /oauth/error：授权服务错误信息端点
     *      * /oauth/check_token：用于资源服务访问的令牌解析端点
     *      * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     */

    /**
     * 设置保存token的方式，一共有五种
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    @Autowired
    private ClientDetailsService myClientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private CustomAuthExceptionHandler customAuthExceptionHandler;

    /**
     * 配置认证服务器
     *
     * @return
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
            // 允许所有资源服务器访问公钥端点（/oauth/token_key）
            // 只允许验证用户访问令牌解析端点（/oauth/check_token）
            .tokenKeyAccess("permitAll()")
            // isAuthenticated
            .checkTokenAccess("isAuthenticated()")
            // 允许客户端发送表单来进行权限认证来获取令牌
            .allowFormAuthenticationForClients()
            //
            .authenticationEntryPoint(customAuthExceptionHandler)
            //
            .accessDeniedHandler(customAuthExceptionHandler);

        // String realm = "oauth2/client";
        // ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter =
        // new ClientCredentialsTokenEndpointFilter();
        // clientCredentialsTokenEndpointFilter.setAuthenticationManager(authenticationManager);
        // OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        // authenticationEntryPoint.setTypeName("Form");
        // authenticationEntryPoint.setRealmName(realm);
        // authenticationEntryPoint.setExceptionRenderer(new HeaderOnlyOAuth2ExceptionRenderer());
        //
        // clientCredentialsTokenEndpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint);
        //
        // security.addTokenEndpointAuthenticationFilter(clientCredentialsTokenEndpointFilter);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(myClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(userDetailsService);

        // 设置token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter, tokenEnhancer));
        endpoints.tokenStore(tokenStore).tokenEnhancer(tokenEnhancerChain);
        endpoints.reuseRefreshTokens(true);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // token有效期设置2个小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 2);
        // Refresh_token:30天
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 30);

        endpoints.tokenServices(tokenServices);

        //
        endpoints.authorizationCodeServices(authorizationCodeServices);
        // 密码授权模式
        endpoints.authenticationManager(authenticationManager);
        //
        endpoints.setClientDetailsService(myClientDetailsService);
        // 处理 ExceptionTranslationFilter 抛出的异常
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);

        // OAuth2AuthenticationEntryPoint o;
        // ClientCredentialsTokenEndpointFilter f = null;
        // BasicAuthenticationFilter b = null;
        // ExceptionTranslationFilter e = null;
        // AnonymousAuthenticationFilter a = null;
        // BasicErrorController basicErrorController;

    }

    /**
     * 注册一个ClientDetailsService用户clientId和clientSecret验证
     * 这里系统已经有默认注册了，所以强制指定它为主
     * @formatter:off
     * 
     * BaseClientDetails属性如下:
     *
     * getClientId：clientId，唯一标识，不能为空
     * getClientSecret：clientSecret，密码
     * isSecretRequired：是否需要验证密码
     * getScope：可申请的授权范围
     * isScoped：是否需要验证授权范围
     * getResourceIds：允许访问的资源id，这个涉及到资源服务器
     * getAuthorizedGrantTypes：可使用的Oauth2授权模式，不能为空
     * getRegisteredRedirectUri：回调地址，用户在authorization_code模式下接收授权码code
     * getAuthorities：授权，这个完全等同于SpringSecurity本身的授权
     * getAccessTokenValiditySeconds：access_token过期时间，单位秒。null等同于不过期
     * getRefreshTokenValiditySeconds：refresh_token过期时间，单位秒。null等同于getAccessTokenValiditySeconds，0或者无效数字等同于不过期
     * isAutoApprove：判断是否获得用户授权scope
     * 
     * @formatter:on
     * @param oauth2Service
     * @return
     */
    @Primary
    @Bean
    public ClientDetailsService myClientDetailsService(IOauth2Service oauth2Service) {
        return clientId -> {
            List<OauthClientDetails> clients1 = oauth2Service.findOauth2ClientByClientId(clientId);
            if (clients1 == null || clients1.isEmpty()) {
                throw new InvalidClientException("invalid client_id");
            }
            OauthClientDetails client = clients1.get(0);

            if (GlobalEnabledEnum.DISABLE.equals(client.getEnabled())) {
                throw new InvalidClientException("invalid client");
            }
            String clientSecretAfterEncoder = client.getClientSecret();
            // String clientSecretAfterEncoder = passwordEncoder.encode(client.getClientSecret());
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(client.getClientId());
            clientDetails.setClientSecret(clientSecretAfterEncoder);
            clientDetails
                .setRegisteredRedirectUri(new HashSet<>(Arrays.asList(client.getWebServerRedirectUri().split(","))));
            clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getAuthorizedGrantTypes().split(",")));
            clientDetails.setScope(Arrays.asList(client.getScope().split(",")));

            return clientDetails;
        };
    }

    /**
     * 注册一个AuthorizationCodeServices以保存authorization_code的授权码code
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, OAuth2Authentication> myRedisTemplate = new RedisTemplate<>();
        myRedisTemplate.setConnectionFactory(redisConnectionFactory);
        myRedisTemplate.afterPropertiesSet();
        return new RandomValueAuthorizationCodeServices() {

            @Override
            protected void store(String code, OAuth2Authentication authentication) {
                myRedisTemplate.boundValueOps(code).set(authentication, 10, TimeUnit.MINUTES);
            }

            @Override
            protected OAuth2Authentication remove(String code) {
                OAuth2Authentication authentication = myRedisTemplate.boundValueOps(code).get();
                myRedisTemplate.delete(code);
                return authentication;
            }
        };
    }

}