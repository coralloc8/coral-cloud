
package com.coral.database.test.config;

import java.util.Map;

import javax.sql.DataSource;

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

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryMysql",
    transactionManagerRef = "transactionManagerMysql",
    basePackages = {PrimaryJpaConfig.COMMON_DAO_PACKAGE, PrimaryJpaConfig.DEFAULT_DAO_PACKAGE})

@Slf4j
public class PrimaryJpaConfig {

    protected static final String COMMON_DAO_PACKAGE = "com.coral.base.common.jpa.repository";

    protected static final String DEFAULT_DAO_PACKAGE = "com.coral.database.test.repository";

    protected static final String COMMON_ENTITY_PACKAGE = "com.coral.base.common.jpa.entity";

    protected static final String DEFAULT_ENTITY_PACKAGE = "com.coral.database.test.entity";

    protected static final String DEFAULT_DTO_PACKAGE = "com.coral.database.test.dto";

    @Qualifier("primaryDataSource")
    @Autowired
    private DataSource primaryDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "entityManagerFactoryMysql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMysql(EntityManagerFactoryBuilder builder) {
        // 设置实体类所在位置
        return builder.dataSource(primaryDataSource).properties(getVendorProperties())
            .packages(COMMON_ENTITY_PACKAGE, DEFAULT_ENTITY_PACKAGE, DEFAULT_DTO_PACKAGE)
            .persistenceUnit("mysqlPersistenceUnit").build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "transactionManagerMysql")
    public TransactionManager transactionManagerMysql(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactoryMysql(builder).getObject());
        return txManager;
    }

}
