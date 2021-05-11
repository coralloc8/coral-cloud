package com.coral.enable.log.aop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coral.base.common.json.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.coral.base.common.jpa.enums.GlobalYesOrNoEnum;
import com.coral.enable.log.annotation.LogAnnotation;
import com.coral.enable.log.service.SysLogService;
import com.coral.database.test.entity.SysLog;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Aspect
@Slf4j
@Component
public class LogRecordAop {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Around("@annotation(ds)")
    public Object around(ProceedingJoinPoint joinPoint, LogAnnotation ds) throws Throwable {

        log.info(">>>>>[日志记录开始]...");
        /**
         * 不保存日志的话
         */
        if (!ds.recordRequestParam()) {
            return joinPoint.proceed();
        }

        Object result;
        SysLog sysLog = new SysLog();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 获取方法参数

            List<Object> httpReqArgs = new ArrayList<>();

            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);

            // 参数值
            Object[] args = joinPoint.getArgs();

            String params = null;
            for (Object object : args) {
                if (object instanceof HttpServletRequest) {
                    request = (HttpServletRequest)object;
                } else if (object instanceof HttpServletResponse) {
                    //
                } else {
                    httpReqArgs.add(object);
                }
            }
            String moudle =
                logAnnotation.value() + ":" + methodSignature.getDeclaringTypeName() + "/" + methodSignature.getName();
            sysLog.setModule(moudle);

            if (!Objects.isNull(request)) {
                String ip = request.getRemoteAddr();
                String httpMethod = request.getMethod();
                String url = request.getRequestURL().toString();
                String requestId = request.getRequestedSessionId();

                sysLog.setUrl(url);
                sysLog.setIp(ip);
                sysLog.setMethod(httpMethod);
                sysLog.setRequestId(requestId);
            }

            sysLog.setCreateTime(LocalDateTime.now());

            params = JsonUtil.toJson(httpReqArgs);
            sysLog.setParams(params);

            // 打印请求参数参数

            // 调用原来的方法
            result = joinPoint.proceed();
            sysLog.setStatus(GlobalYesOrNoEnum.YES);

        } catch (Exception e) {
            sysLog.setStatus(GlobalYesOrNoEnum.NO);
            sysLog.setError(e.getMessage());
            log.error(">>>Error:", e);
            throw e;
        } finally {
            CompletableFuture.runAsync(() -> {
                try {
                    log.debug("日志落库开始：{}", sysLog);
                    if (sysLogService != null) {
                        sysLogService.save(sysLog);
                    }
                    log.debug("开始落库结束：{}", sysLog);
                } catch (Exception e) {
                    log.error("落库失败：{}", e.getMessage());
                }
            }, taskExecutor);
        }
        log.info(">>>>>[日志记录结束]...");
        return result;
    }

}
