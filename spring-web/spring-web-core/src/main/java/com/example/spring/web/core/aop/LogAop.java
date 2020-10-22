package com.example.spring.web.core.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Aspect
@Slf4j
public class LogAop {

    @Pointcut("execution(public * com.example.spring.web..*.*Controller..*(..)) ||"
        + " execution(public * com.example.spring.web..*.*Control..*(..)) ||"
        + " execution(public * org.springframework.security.oauth2.provider.endpoint.TokenEndpoint..*(..))")
    public void webLog() {

    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("[aop request start].....");
        log.info("@@@@@<URL>: {}, <HTTP_METHOD>: {}, <IP>: {}, <CLASS_METHOD>: {}, <ARGS>: {}",
            request.getRequestURL().toString(), request.getMethod(), request.getRemoteAddr(),
            pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName(),
            Arrays.toString(pjp.getArgs()));

        long start = System.currentTimeMillis();
        Object o = null;
        try {
            o = pjp.proceed();
        } catch (Throwable e) {
            log.error("[aop error]: {}", e.getMessage());
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            log.info("[aop request end]....., total time-consuming: {} ms, result is: {}", (end - start), o);
        }
        return o;
    }

}
