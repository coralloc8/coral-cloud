package com.coral.cloud.monitor.common.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @description 自定义事务拦截器
 * @date 2021/5/20 16:55
 */
@Aspect
@Configuration
@Slf4j
public class TransactionAdviceConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.zhyx..*.*Service.*(..))";

    private static final int TX_METHOD_TIMEOUT = 300;

    @Autowired
    private TransactionManager transactionManager;


    @Bean("cusTransactionInterceptor")
    public TransactionInterceptor cusTransactionInterceptor() {
        return create(transactionManager);
    }

    @Bean("cusAdvisor")
    public Advisor cusAdvisor() {
        return txAdviceAdvisor(cusTransactionInterceptor());
    }


    private TransactionInterceptor create(TransactionManager transactionManager) {
        log.info(">>>>>TransactionInterceptor create {}...", transactionManager);
        RuleBasedTransactionAttribute required =
                new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                        Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        required.setTimeout(TX_METHOD_TIMEOUT);

        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        readOnly.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        source.addTransactionalMethod("save*", required);
        source.addTransactionalMethod("add*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("delete*", required);
        source.addTransactionalMethod("remove*", required);
        source.addTransactionalMethod("update*", required);
        source.addTransactionalMethod("modify*", required);
        source.addTransactionalMethod("edit*", required);
        source.addTransactionalMethod("exec*", required);
        source.addTransactionalMethod("set*", required);
        source.addTransactionalMethod("copy*", required);
        source.addTransactionalMethod("sort*", required);
        source.addTransactionalMethod("submit*", required);
        source.addTransactionalMethod("send*", required);
        source.addTransactionalMethod("import*", required);

        source.addTransactionalMethod("*Save", required);
        source.addTransactionalMethod("*Add", required);
        source.addTransactionalMethod("*Insert", required);
        source.addTransactionalMethod("*Delete", required);
        source.addTransactionalMethod("*Deleted", required);
        source.addTransactionalMethod("*Enabled", required);
        source.addTransactionalMethod("*Disabled", required);
        source.addTransactionalMethod("*Remove", required);
        source.addTransactionalMethod("*Update", required);
        source.addTransactionalMethod("*Modified", required);
        source.addTransactionalMethod("*Modify", required);
        source.addTransactionalMethod("*Edit", required);
        source.addTransactionalMethod("*Exec", required);
        source.addTransactionalMethod("*Set", required);
        source.addTransactionalMethod("*Copy", required);
        source.addTransactionalMethod("*Sort", required);
        source.addTransactionalMethod("*Submit", required);


        source.addTransactionalMethod("get*", readOnly);
        source.addTransactionalMethod("query*", readOnly);
        source.addTransactionalMethod("find*", readOnly);
        source.addTransactionalMethod("list*", readOnly);
        source.addTransactionalMethod("count*", readOnly);
        source.addTransactionalMethod("is*", readOnly);
        source.addTransactionalMethod("exist*", readOnly);
        source.addTransactionalMethod("page*", readOnly);
        source.addTransactionalMethod("check*", readOnly);

        return new TransactionInterceptor(transactionManager, source);
    }

    private Advisor txAdviceAdvisor(TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }


}
