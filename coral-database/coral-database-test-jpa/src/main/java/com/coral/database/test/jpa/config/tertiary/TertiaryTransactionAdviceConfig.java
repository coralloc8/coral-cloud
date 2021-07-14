package com.coral.database.test.jpa.config.tertiary;

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
public class TertiaryTransactionAdviceConfig extends BaseTransactionInterceptor {


    @Qualifier("tertiaryTransactionManager")
    @Autowired
    private TransactionManager tertiaryTransactionManager;

    @Bean("tertiaryTransactionInterceptor")
    public TransactionInterceptor tertiaryTransactionInterceptor() {
        return create(tertiaryTransactionManager);
    }

    @Bean("tertiaryAdvisor")
    public Advisor secondaryAdvisor() {
        return txAdviceAdvisor(tertiaryTransactionInterceptor());
    }
}
