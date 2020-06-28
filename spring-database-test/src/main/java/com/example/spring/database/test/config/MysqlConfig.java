
package com.example.spring.database.test.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

/**
 * @author huss
 */
@Configuration
@ConditionalOnBean(DataSourceConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryMysql",
    transactionManagerRef = "transactionManagerMysql",
    basePackages = {MysqlConfig.COMMON_DAO_PACKAGE, MysqlConfig.DEFAULT_DAO_PACKAGE})
public class MysqlConfig {

    protected static final String COMMON_DAO_PACKAGE = "com.example.spring.common.jpa.repository";

    protected static final String DEFAULT_DAO_PACKAGE = "com.example.spring.database.test.repository";

    protected static final String COMMON_ENTITY_PACKAGE = "com.example.spring.common.jpa.entity";

    protected static final String DEFAULT_DTO_PACKAGE = "com.example.spring.database.test.dto";

    protected static final String DEFAULT_ENTITY_PACKAGE = "com.example.spring.database.test.entity";

    @Qualifier("mysqlDataSource")
    @Autowired
    private DataSource mysqlDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "entityManagerMysql")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryMysql(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryMysql")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMysql(EntityManagerFactoryBuilder builder) {
        // 设置实体类所在位置
        return builder.dataSource(mysqlDataSource).properties(getVendorProperties())
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
