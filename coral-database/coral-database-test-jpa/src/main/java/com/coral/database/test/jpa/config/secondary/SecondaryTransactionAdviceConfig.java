package com.coral.database.test.jpa.config.secondary;

import com.coral.database.test.jpa.config.BaseTransactionInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author huss
 */
@Aspect
@Configuration
public class SecondaryTransactionAdviceConfig extends BaseTransactionInterceptor {


    @Qualifier("secondaryTransactionManager")
    @Autowired
    private TransactionManager secondaryTransactionManager;

    @Bean("secondaryTransactionInterceptor")
    public TransactionInterceptor secondaryTransactionInterceptor() {
        return create(secondaryTransactionManager);
    }

    @Bean("secondaryAdvisor")
    public Advisor secondaryAdvisor() {
        return txAdviceAdvisor(secondaryTransactionInterceptor());
    }
}
