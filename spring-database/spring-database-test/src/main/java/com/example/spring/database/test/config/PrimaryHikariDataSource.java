//package com.example.spring.database.test.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//import com.zaxxer.hikari.HikariDataSource;
//
//import lombok.Getter;
//import lombok.ToString;
//
///**
// * @description: TODO
// * @author: huss
// * @time: 2020/7/10 18:28
// */
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource.primary")
//@ToString(callSuper = true)
//public class PrimaryHikariDataSource extends HikariDataSource {
//    /**
//     * JDBC URL of the database.
//     */
//    @Getter
//    private String url;
//
//    public void setUrl(String url) {
//        this.url = url;
//        this.setJdbcUrl(url);
//    }
//}
