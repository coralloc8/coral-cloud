package com.coral.database.test.jpa.config.primary;

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
public class PrimaryTransactionAdviceConfig extends BaseTransactionInterceptor {


    @Qualifier("primaryTransactionManager")
    @Autowired
    private TransactionManager primaryTransactionManager;

    @Bean("primaryTransactionInterceptor")
    public TransactionInterceptor primaryTransactionInterceptor() {
        return create(primaryTransactionManager);
    }

    @Bean("primaryAdvisor")
    public Advisor primaryAdvisor() {
        return txAdviceAdvisor(primaryTransactionInterceptor());
    }
}
