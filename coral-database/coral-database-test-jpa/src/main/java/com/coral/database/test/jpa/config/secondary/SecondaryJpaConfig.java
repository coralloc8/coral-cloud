
package com.coral.database.test.jpa.config.secondary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author huss
 */
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {SecondaryJpaConfig.COMMON_DAO_PACKAGE, SecondaryJpaConfig.DEFAULT_DAO_PACKAGE})

@Slf4j
public class SecondaryJpaConfig {

    protected static final String COMMON_DAO_PACKAGE = "com.coral.base.common.jpa.repository";

    protected static final String DEFAULT_DAO_PACKAGE = "com.coral.database.test.jpa.secondary.repository";

    protected static final String COMMON_ENTITY_PACKAGE = "com.coral.base.common.jpa.entity";

    protected static final String DEFAULT_ENTITY_PACKAGE = "com.coral.database.test.jpa.secondary.entity";

    protected static final String DEFAULT_DTO_PACKAGE = "com.coral.database.test.jpa.secondary.dto";


    @Qualifier("secondaryDataSource")
    @Autowired
    private DataSource secondaryDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "secondaryEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return secondaryEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        // 设置实体类所在位置
        return builder.dataSource(secondaryDataSource)
                .properties(getVendorProperties())
                .packages(COMMON_ENTITY_PACKAGE, DEFAULT_ENTITY_PACKAGE, DEFAULT_DTO_PACKAGE)
                .persistenceUnit("secondaryPersistenceUnit").build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "secondaryTransactionManager")
    public TransactionManager secondaryTransactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(secondaryEntityManagerFactory(builder).getObject());
        return txManager;
    }

}
