package com.coral.database.test.jpa.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
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
 * @className TransactionInterceptor
 * @description 事务通用拦截器
 * @date 2021/5/17 16:26
 */
public class BaseTransactionInterceptor {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.coral..*.service.*.*(..))";

    public TransactionInterceptor create(TransactionManager transactionManager) {
        RuleBasedTransactionAttribute required =
                new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                        Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // required.setTimeout(5);

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
