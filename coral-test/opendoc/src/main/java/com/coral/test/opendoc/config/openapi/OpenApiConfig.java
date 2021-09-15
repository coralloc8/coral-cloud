package com.coral.test.opendoc.config.openapi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author huss
 * @version 1.0
 * @className SwaggerConfig
 * @description swagger 自定义配置
 * @date 2021/7/8 17:50
 */
@Configuration
@Slf4j
public class OpenApiConfig {

    private final OpenApiProperties openApiProperties;

    private final ApplicationContext context;

    @Getter
    private Map<String, OpenApiProperties.Group> groups;

    /**
     * 3.0注解变化 2.0的注解依然可以使用
     * 地址变为 swagger-ui/index.html
     *
     * @param openApiProperties
     * @ApiParam -> @Parameter
     * @ApiOperation -> @Operation
     * @Api -> @Tag
     * @ApiImplicitParams -> @Parameters
     * @ApiImplicitParam -> @Parameter
     * @ApiIgnore -> @Parameter(hidden = true) or @Operation(hidden = true) or @Hidden
     * @ApiModel -> @Schema
     * @Schema -> @Schema
     */
    public OpenApiConfig(OpenApiProperties openApiProperties, ApplicationContext context) {
        this.openApiProperties = openApiProperties;
        this.context = context;
        this.groups = openApiProperties.getGroups().stream()
                .collect(Collectors.toMap(e -> e.getGroup(), Function.identity()));
    }


    public void printVersion() {
        log.info("============================= openapi信息如下 =============================");
        String version = "Application Version: " + openApiProperties.getVersion() + ", " +
                "Spring Boot Version: " + SpringBootVersion.getVersion();
        log.info("#####项目版本号为:{}", version);
        log.info("#####项目访问地址为:{}", openApiProperties.getUrl());
        log.info("=========================================================================");
    }


    /**
     * 初始化tags
     *
     * @param group
     * @return
     */
    private List<Tag> initTags(OpenApiProperties.Group group) {
        return group.getTags().stream()
                .map(e -> new Tag().name(e.getName()).description(e.getDesc()))
                .collect(Collectors.toList());
    }


    private List<Tag> allTags() {
        List<Tag> tags = getGroups().entrySet().stream()
                .map(e -> initTags(e.getValue()))
                .flatMap(e -> e.stream())
                .collect(Collectors.toList());
        return tags;
    }

    private GroupedOpenApi createGroupedOpenApi(String groupName) {
        OpenApiProperties.Group group = getGroups().get(groupName);
        GroupedOpenApi.Builder builder = GroupedOpenApi.builder()
                .group(group.getGroup())
                .addOpenApiCustomiser(api -> api.tags(initTags(group)))

                ;

        if (StringUtils.isNotBlank(group.getPackagesToScan())) {
            builder.packagesToScan(group.getPackagesToScan());
        }

        if (StringUtils.isNotBlank(group.getPackagesToExclude())) {
            builder.packagesToExclude(group.getPackagesToExclude());
        }


        return builder.build();
    }

    @Bean
    public GroupedOpenApi app() {
        return this.createGroupedOpenApi("app");
    }

    @Bean
    public GroupedOpenApi admin() {
        return this.createGroupedOpenApi("admin");
    }


    @Bean
    public OpenAPI createRestApi() {
        return new OpenAPI()
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .info(apiInfo())
                //授权
                .components(components())
                //所有tags
                .tags(allTags())
                //添加security
                .addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .addSecurityItem(new SecurityRequirement().addList("LoginToken"))
                ;
    }


    private Components components() {
        return new Components()
                .addSecuritySchemes("LoginToken", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .name("token")
                        .in(In.HEADER)
                        .description("pc端登录必须token")
                )
                .addSecuritySchemes("Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("app端登录必须jwt token")
                                .flows(
                                        new OAuthFlows()
                                )
                )
                ;
    }

    /**
     * API 页面上半部分展示信息
     */
    private Info apiInfo() {
        return new Info()
                .title(openApiProperties.getName())
                .description(openApiProperties.getDescription())
                .contact(new Contact().name("智慧医学").email("452327322@qq.com"))
                .version("Application Version: " + openApiProperties.getVersion() + ", " +
                        "Spring Boot Version: " + SpringBootVersion.getVersion())
                ;
    }


}
