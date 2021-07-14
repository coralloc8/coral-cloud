
package com.coral.database.test.jpa.config.tertiary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author huss
 */
@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "tertiaryEntityManagerFactory",
        transactionManagerRef = "tertiaryTransactionManager",
        basePackages = {TertiaryJpaConfig.COMMON_DAO_PACKAGE, TertiaryJpaConfig.DEFAULT_DAO_PACKAGE})
@Slf4j
public class TertiaryJpaConfig {

    protected static final String COMMON_DAO_PACKAGE = "com.coral.base.common.jpa.repository";

    protected static final String DEFAULT_DAO_PACKAGE = "com.coral.database.test.jpa.tertiary.repository";

    protected static final String COMMON_ENTITY_PACKAGE = "com.coral.base.common.jpa.entity";

    protected static final String DEFAULT_ENTITY_PACKAGE = "com.coral.database.test.jpa.tertiary.entity";

    protected static final String DEFAULT_DTO_PACKAGE = "com.coral.database.test.jpa.tertiary.dto";


    @Qualifier("tertiaryDataSource")
    @Autowired
    private DataSource secondaryDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "tertiaryEntityManager")
    public EntityManager tertiaryEntityManager(EntityManagerFactoryBuilder builder) {
        return tertiaryEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "tertiaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tertiaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        // 设置实体类所在位置
        return builder.dataSource(secondaryDataSource)
                .properties(getVendorProperties())
                .packages(COMMON_ENTITY_PACKAGE, DEFAULT_ENTITY_PACKAGE, DEFAULT_DTO_PACKAGE)
                .persistenceUnit("tertiaryPersistenceUnit").build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "tertiaryTransactionManager")
    public TransactionManager tertiaryTransactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(tertiaryEntityManagerFactory(builder).getObject());
        return txManager;
    }

}
