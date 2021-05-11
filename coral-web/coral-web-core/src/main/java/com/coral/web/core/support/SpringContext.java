package com.coral.web.core.support;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * @author huss
 */
public class SpringContext {

    public static <T> T getBeanByName(Class<T> clazz, String name) {
        try {
            ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
            return ctx.getBean(name, clazz);
        } catch (Exception var6) {
            ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

            try {
                return RequestContextUtils.findWebApplicationContext(servletRequestAttributes.getRequest())
                    .getBean(name, clazz);
            } catch (Exception var5) {
                return null;
            }
        }
    }

    public static <T> T getBeanByType(Class<T> clazz) {
        try {
            ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
            return ctx.getBean(clazz);
        } catch (Exception var5) {
            ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

            try {
                return RequestContextUtils.findWebApplicationContext(servletRequestAttributes.getRequest())
                    .getBean(clazz);
            } catch (Exception var4) {
                return null;
            }
        }
    }
}
