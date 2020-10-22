package com.example.spring.service.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.example.spring.common.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Configuration
@ConfigurationProperties(prefix = "upload")
@Data
@Slf4j
public class UploadProperty {

    private String savePath;

    private String domain;

    private String virtualPath;

    public String getFullBasePath() {
        return this.getDomain() + StringUtils.getFilePth(this.getVirtualPath());
    }

    public String getNetworkFullPath(String relativePath) {
        return this.getFullBasePath() + StringUtils.getFilePth(relativePath);
    }

}
