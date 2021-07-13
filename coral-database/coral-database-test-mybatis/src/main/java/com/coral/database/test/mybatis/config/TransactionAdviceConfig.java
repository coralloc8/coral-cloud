package com.coral.database.test.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

/**
 * @author huss
 * @version 1.0
 * @className TransactionAdviceConfig
 * @description
 * @date 2021/5/20 16:55
 */
@Aspect
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
@Slf4j
public class TransactionAdviceConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.coral..service..*.*(..))";

    private static final int TX_METHOD_TIMEOUT = 300;

    @Qualifier("primaryTransactionManager")
    @Autowired
    private TransactionManager primaryTransactionManager;

    @Qualifier("secondaryTransactionManager")
    @Autowired
    private TransactionManager secondaryTransactionManager;


    @Qualifier("tertiaryTransactionManager")
    @Autowired
    private TransactionManager tertiaryTransactionManager;


    @Bean("primaryTransactionInterceptor")
    public TransactionInterceptor primaryTransactionInterceptor() {
        return create(primaryTransactionManager);
    }

    @Bean("primaryAdvisor")
    public Advisor primaryAdvisor() {
        return txAdviceAdvisor(primaryTransactionInterceptor());
    }


    ///////////////////////

    @Bean("secondaryTransactionInterceptor")
    public TransactionInterceptor secondaryTransactionInterceptor() {
        return create(secondaryTransactionManager);
    }

    @Bean("secondaryPrimaryAdvisor")
    public Advisor secondaryPrimaryAdvisor() {
        return txAdviceAdvisor(secondaryTransactionInterceptor());
    }

    ///////////////////////


    @Bean("tertiaryTransactionInterceptor")
    public TransactionInterceptor tertiaryTransactionInterceptor() {
        return create(tertiaryTransactionManager);
    }

    @Bean("tertiaryPrimaryAdvisor")
    public Advisor tertiaryPrimaryAdvisor() {
        return txAdviceAdvisor(tertiaryTransactionInterceptor());
    }





    public TransactionInterceptor create(TransactionManager transactionManager) {
        log.info(">>>>>TransactionInterceptor create...");
        RuleBasedTransactionAttribute required =
                new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                        Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        required.setTimeout(TX_METHOD_TIMEOUT);

        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        readOnly.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        source.addTransactionalMethod("save*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("delete*", required);
        source.addTransactionalMethod("remove*", required);
        source.addTransactionalMethod("update*", required);
        source.addTransactionalMethod("modify*", required);
        source.addTransactionalMethod("edit*", required);
        source.addTransactionalMethod("exec*", required);
        source.addTransactionalMethod("set*", required);

        source.addTransactionalMethod("get*", readOnly);
        source.addTransactionalMethod("query*", readOnly);
        source.addTransactionalMethod("find*", readOnly);
        source.addTransactionalMethod("list*", readOnly);
        source.addTransactionalMethod("count*", readOnly);
        source.addTransactionalMethod("is*", readOnly);
        source.addTransactionalMethod("exist*", readOnly);
        source.addTransactionalMethod("page*", readOnly);

        return new TransactionInterceptor(transactionManager, source);
    }

    public Advisor txAdviceAdvisor(TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }


}
