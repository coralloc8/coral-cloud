package com.example.spring.database.test.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@EnableConfigurationProperties(MyDataSource.class)
@Configuration
@Slf4j
public class DataSourceConfig {

	@Bean("mysqlDataSource")
	@Primary
	public DataSource mysqlSDataSource() {
		log.info("#####create mysqlSDataSource bean...");
		return new MyDataSource();
	}

	@Bean(name = "mysqlJdbcTemplate")
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		log.info("#####create mysqlJdbcTemplate bean...");
		return new JdbcTemplate(dataSource);
	}

}
