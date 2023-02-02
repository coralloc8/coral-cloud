package com.coral.test.spring.event.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Configuration;

/**
 * @author huss
 * @version 1.0
 * @className TestProperty
 * @description todo
 * @date 2023/2/2 13:47
 */
@ConfigurationProperties(prefix = "test")
@Configuration
@ConfigurationPropertiesBinding
@Data
public class TestProperty {

    private String name;
    private YesOrNoEnum code;

    private YesOrNoEnum isShow;

    public void setCode(String code) {
        this.code = YesOrNoEnum.codeOf(code);
    }
}
