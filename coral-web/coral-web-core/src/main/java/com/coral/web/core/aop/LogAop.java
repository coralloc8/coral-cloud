package com.coral.web.core.aop;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coral.base.common.AnnotationUtil;
import com.coral.base.common.json.JsonUtil;
import com.coral.web.core.annotation.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huss
 */
@Aspect
@Slf4j
public class LogAop {

    private static final List<String> URLS = Arrays.asList("login", "logout");

    @Pointcut("execution(public * com.coral..*.*Controller..*(..)) ||"
        + " execution(public * com.coral..*.*Control..*(..)) ||"
        + " execution(public * org.springframework.security.oauth2.provider.endpoint.TokenEndpoint..*(..))")
    public void webLog() {

    }

//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
//            "@annotation(org.springframework.web.bind.annotation.PostMapping)  ||" +
//            "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
//            "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
//            "@annotation(org.springframework.web.bind.annotation.DeleteMapping) ||" +
//            "@annotation(org.springframework.web.bind.annotation.PatchMapping)")
//    public void webLog() {
//    }


    @Around("webLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(">>>>>[日志记录开始]...");
        String module = "";
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Optional<Log> logAnnotationOpt = AnnotationUtil.findAnnotation(methodSignature.getMethod(), Log.class);
            boolean can = !logAnnotationOpt.isPresent() ||
                    (logAnnotationOpt.isPresent() && logAnnotationOpt.get().recordRequestParam());

            if (can) {
                // 参数值
                Object[] args = joinPoint.getArgs();

                // 获取方法参数
                //文件上传时file会序列化失败，暂时过滤掉
                List<Object> httpReqArgs = streamOf(args)
                        .filter(arg -> (!(arg instanceof HttpServletRequest) &&
                                !(arg instanceof HttpServletResponse)) &&
                                !(arg instanceof MultipartFile))
                        .collect(Collectors.toList());

                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                boolean canToJson = true;
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    if (!Objects.isNull(request)) {
                        String ip = request.getRemoteAddr();
                        String httpMethod = request.getMethod();
                        String url = request.getRequestURL().toString();
                        String requestId = request.getRequestedSessionId();


                        if (URLS.stream().anyMatch(e -> url.contains(e))) {
                            canToJson = false;
                        }

                        // 打印请求参数参数
                        log.info(">>>>>[url]:{},[method]:{},[ip]:{},session:{}", url, httpMethod, ip, requestId);
                        log.info(">>>>>[header]：{}", this.printHeaders(request));
                    }

                    //重新设置 子线程共享
                    RequestContextHolder.setRequestAttributes(attributes, true);
                }
                String logVal = logAnnotationOpt.isPresent() ? logAnnotationOpt.get().value() + ":" : "";
                module = logVal + methodSignature.getDeclaringTypeName() + "/" + methodSignature.getName();

                //json序列化时在spring auth中的数据时会StackOverflowError
                if (canToJson) {
                    log.info(">>>>>[module]:{},[params]:{}", module, JsonUtil.toJson(httpReqArgs));
                } else {
                    log.info(">>>>>[module]:{},[params]:{}", module, httpReqArgs);
                }

            }
        } catch (Exception e) {
            log.error(">>>>>打印日志出错,Error:", e);
        }
        Object result;
        try {
            // 调用原来的方法
            result = joinPoint.proceed();
            log.info(">>>>>调用结束[{}],返参:{}", module, JsonUtil.toJson(result));
        } catch (Exception e) {
            log.error(">>>>>调用业务接口[" + module + "]出错,Error:", e);
            throw e;
        } finally {
            log.info(">>>>>[日志记录结束]...");
        }
        return result;
    }

    /**
     * stream 转换
     *
     * @param array
     * @param <T>
     * @return
     */
    private static <T> Stream<T> streamOf(T[] array) {
        return ArrayUtils.isEmpty(array) ? Stream.empty() : Arrays.asList(array).stream();
    }


    private List<String> printHeaders(HttpServletRequest request) {
        /**
         * 获取所有请求头信息
         */
        Enumeration<String> er = request.getHeaderNames();
        List<String> headerNames = new ArrayList<>();
        while (er.hasMoreElements()) {
            headerNames.add(er.nextElement());
        }
        return headerNames;
    }

}
