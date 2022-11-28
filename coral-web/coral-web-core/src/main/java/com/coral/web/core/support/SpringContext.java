package com.coral.web.core.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author huss
 */
@Slf4j
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    public static <T> T getBeanByName(Class<T> clazz, String name) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
//        try {
//            if (Objects.isNull(ctx)) {
//                ctx = ContextLoader.getCurrentWebApplicationContext();
//            }
//        } catch (Exception var5) {
//            ServletRequestAttributes servletRequestAttributes =
//                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            try {
//                ctx = RequestContextUtils.findWebApplicationContext(servletRequestAttributes.getRequest());
//
//            } catch (Exception var4) {
//                throw Exceptions.unchecked(var4);
//            }
//
//        }
    }


    public static Map<String, Object> getAllBeans() {
        return getApplicationContext().getBeansOfType(Object.class);
    }

    public static <T> T getBeanByType(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info(">>>>>init applicationContext.");
        APPLICATION_CONTEXT = applicationContext;
    }
}
