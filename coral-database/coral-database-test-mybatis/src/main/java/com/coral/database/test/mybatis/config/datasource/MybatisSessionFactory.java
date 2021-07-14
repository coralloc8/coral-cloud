package com.coral.database.test.mybatis.config.datasource;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author huss
 * @version 1.0
 * @className MybatisSessionFactory
 * @description 自定义session factory
 * @date 2021/7/14 9:05
 */
@Slf4j
@Component
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisSessionFactory {

    private final MybatisPlusProperties properties;

    private final Interceptor[] interceptors;

    private final TypeHandler[] typeHandlers;

    private final ApplicationContext applicationContext;

    public MybatisSessionFactory(MybatisPlusProperties properties,
                                 ObjectProvider<Interceptor[]> interceptorsProvider,
                                 ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                 ApplicationContext applicationContext) {
        this.properties = properties;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.typeHandlers = typeHandlersProvider.getIfAvailable();
        this.applicationContext = applicationContext;
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocation) throws Exception {
        log.info(">>>>>dataSource:{},mapperLocation:{}", dataSource, mapperLocation);
        //  使用 MybatisSqlSessionFactoryBean 而不是 SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        if (Objects.nonNull(this.interceptors)) {
            factory.setPlugins(interceptors);
        }

        //mapper.xml 映射
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));

        if (this.properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(this.properties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }

        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (this.properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.typeHandlers)) {
            factory.setTypeHandlers(this.typeHandlers);
        }

        //  自定义枚举包
        if (StringUtils.hasLength(this.properties.getTypeEnumsPackage())) {
            factory.setTypeEnumsPackage(this.properties.getTypeEnumsPackage());
        }
        //  此处必为非 NULL
        GlobalConfig globalConfig = this.properties.getGlobalConfig();
        //  注入填充器
        this.getBeanThen(MetaObjectHandler.class, globalConfig::setMetaObjectHandler);
        //  注入主键生成器
        this.getBeanThen(IKeyGenerator.class, i -> globalConfig.getDbConfig().setKeyGenerator(i));
        //  注入sql注入器
        this.getBeanThen(ISqlInjector.class, globalConfig::setSqlInjector);
        //  注入ID生成器
        this.getBeanThen(IdentifierGenerator.class, globalConfig::setIdentifierGenerator);
        //  设置 GlobalConfig 到 MybatisSqlSessionFactoryBean
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }

    /**
     * 检查spring容器里是否有对应的bean,有则进行消费
     *
     * @param clazz    class
     * @param consumer 消费
     * @param <T>      泛型
     */
    private <T> void getBeanThen(Class<T> clazz, Consumer<T> consumer) {
        if (this.applicationContext.getBeanNamesForType(clazz, false, false).length > 0) {
            consumer.accept(this.applicationContext.getBean(clazz));
        }
    }

}
