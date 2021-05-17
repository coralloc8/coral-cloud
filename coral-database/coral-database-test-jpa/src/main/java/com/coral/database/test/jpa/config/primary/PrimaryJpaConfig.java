
package com.coral.database.test.jpa.config.primary;

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
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = {PrimaryJpaConfig.COMMON_DAO_PACKAGE, PrimaryJpaConfig.DEFAULT_DAO_PACKAGE})

@Slf4j
public class PrimaryJpaConfig {

    protected static final String COMMON_DAO_PACKAGE = "com.coral.base.common.jpa.repository";

    protected static final String DEFAULT_DAO_PACKAGE = "com.coral.database.test.jpa.primary.repository";

    protected static final String COMMON_ENTITY_PACKAGE = "com.coral.base.common.jpa.entity";

    protected static final String DEFAULT_ENTITY_PACKAGE = "com.coral.database.test.jpa.primary.entity";

    protected static final String DEFAULT_DTO_PACKAGE = "com.coral.database.test.jpa.primary.dto";


    @Qualifier("primaryDataSource")
    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return primaryEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        // 设置实体类所在位置
        return builder.dataSource(primaryDataSource).properties(getVendorProperties())
                .packages(COMMON_ENTITY_PACKAGE, DEFAULT_ENTITY_PACKAGE, DEFAULT_DTO_PACKAGE)
                .persistenceUnit("primaryPersistenceUnit").build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public TransactionManager primaryTransactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(primaryEntityManagerFactory(builder).getObject());
        return txManager;
    }

}
