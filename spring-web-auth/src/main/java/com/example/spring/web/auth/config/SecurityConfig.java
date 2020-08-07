package com.example.spring.web.auth.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.spring.common.jpa.enums.GlobalEnabledEnum;
import com.example.spring.database.test.entity.SysUser;
import com.example.spring.web.auth.security.handler.CustomAuthExceptionHandler;
import com.example.spring.web.auth.service.IOauth2Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * @formatter:off
     * 支持Restfull风格流程为:
     *
     * 1、用户访问第三方client网站
     * 2、第三方网站将用户导向我们的某个前端页面地址并携带参数client_id、scope、redirect_uri
     * 3、该前端页面通过ajax请求后台接口
     *      /oauth/authorize?client_id={client_id}&response_type=code&scope={scope}&redirect_uri={redirect_uri}
     * 4、后端接到请求后SpringSecurity首先会校验参数合法性，不合法则转发到/oauth/error，/oauth/error返回jons结果告知前端参数不合法。
     *      如果参数合法则再判断当前是否已有用户通过认证：有，则会将请求转发到/oauth/confirm_access，/oauth/confirm_access方法返回
     *      json结果告知前端需要用户授权；如果没有则会将请求转发到/login(get请求)，/login方法也返回json结果告知前端需要用户登录。
     * 5、前端页面根据返回结果判断，如果需要登录则跳转到登录页面，如果需要用户授权则跳转到用户授权页面。
     * 6、如果跳转到用户登录页面，用户输入用户名密码点击登录按钮，前端通过ajax请求后台接口/login(post请求)，后端接到请求后
     *      SpringSecurity判断用户认证是否通过：如果通过则转发请求到and().formLogin().successForwardUrl()所设定的uri，该uri返回
     *      json结果告知用户登录成功。如果未通过则转发请求到and().formLogin().failureForwardUrl("/login/error")所设定的uri，
     *      该uri返回json结果告知用户登录失败
     * 7、前端用户登录页面拿到后端返回的登录结果，如果登录失败则继续等待用户填写用户名密码重新登录，如果登录成功则跳转到用户授权页面。
     * 8、用户进行授权勾选并点击确认授权后，前端通过表单post到后台接口/oauth/authorize
     * 9、后端接到请求后处理并重定向会第三方client回调地址
     * @formatter:on
     */

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthExceptionHandler customAuthExceptionHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // @Override
    // public void configure(WebSecurity web) throws Exception {
    // web.ignoring().antMatchers("/login.html", "/static/**");
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        //
        http.authorizeRequests().anyRequest().authenticated();

        // ExceptionTranslationFilter返回的错误信息格式重新定义
        http.exceptionHandling()
            //
            .authenticationEntryPoint(customAuthExceptionHandler)
            //
            .accessDeniedHandler(customAuthExceptionHandler);

    }

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable();
    //
    // // 1.表单登录
    // http.
    //
    // authorizeRequests().anyRequest().authenticated()
    // //
    // .and().formLogin()
    // // 后台登录的页面
    // //.loginPage(securityProperties.getLoginUrl())
    // // 登录请求拦截的url,也就是form表单提交时指定的action
    // //.loginProcessingUrl(securityProperties.getLoginProcessingUrl())
    // // 用户名的请求字段 默认为userName
    // .usernameParameter(securityProperties.getUsernameParameter())
    // // 密码的请求字段 默认为password
    // .passwordParameter(securityProperties.getPasswordParameter())
    // // 成功
    // .successHandler(myAuthenticationSuccessHandler).failureHandler(myAuthenticationFailHandler)
    //
    // // //3.记住我(当需要记住我的)
    // // .rememberMe()
    // // .tokenRepository(persistentTokenRepository())
    // // .tokenValiditySeconds(securityProperties.getRememberMeSeconds())// 有效期20天
    //
    // // 登出
    // .and().logout().invalidateHttpSession(true).logoutUrl(securityProperties.getLogout())
    // .deleteCookies(securityProperties.getJsessionid())
    // // .logoutSuccessHandler(logoutSuccessHandler);
    //
    // // 配置session管理
    // .and().sessionManagement()
    // // session失效默认的跳转地址
    // .invalidSessionUrl(securityProperties.getLoginUrl())
    // // 最大的并发数
    // .maximumSessions(securityProperties.getMaximumSessions())
    // // 之后的登录是否踢掉之前的登录
    // .maxSessionsPreventsLogin(securityProperties.isMaxSessionsPreventsLogin());
    // // .expiredSessionStrategy(sessionInformationExpiredStrategy)
    //
    // }

    /**
     * 注册一个AuthenticationManager用来password模式下用户身份认证
     *
     * @return
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(Collections.singletonList(provider));
    }

    /**
     * 重写PasswordEncoder 接口中的方法，实例化加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 认证服务器不查询用户的权限资源，只负责认证
     * 注册一个UserDetailsService用于用户身份认证
     *
     * @param oauth2Service
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(IOauth2Service oauth2Service) {
        return username -> {
            List<SysUser> users = oauth2Service.findOauth2UserByUsername(username);
            if (users == null || users.isEmpty()) {
                throw new UsernameNotFoundException("invalid username");
            }
            SysUser user = users.get(0);

            if (GlobalEnabledEnum.DISABLE.equals(user.getEnabled())) {
                throw new AccountExpiredException("user account is disabled");
            }
            // String passwordAfterEncoder = passwordEncoder.encode(user.getPassword());
            String passwordAfterEncoder = user.getPassword();

            return User.withUsername(username).password(passwordAfterEncoder).build();
        };
    }

}
