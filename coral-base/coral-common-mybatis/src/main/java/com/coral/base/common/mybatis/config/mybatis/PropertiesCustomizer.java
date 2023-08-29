package com.coral.base.common.mybatis.config.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huss
 * @date 2023-08-22
 */
@Slf4j
public class PropertiesCustomizer implements MybatisPlusPropertiesCustomizer {

    @Getter
    private final String replaceStr;

    private final static String ENTITY_CLASSPATH = "classpath*:com/**/entity/**/*.class";
    private final static String DOMAIN_CLASSPATH = "classpath*:com/**/domain/**/*.class";

    public PropertiesCustomizer(String replaceStr) {
        this.replaceStr = replaceStr;
    }

    @Override
    public void customize(MybatisPlusProperties properties) {
//            log.warn(">>>dataSource: {} \n properties: {}", dataSource, properties);
        if (StringUtils.isBlank(getReplaceStr())) {
            return;
        }
        log.info("##### 加载{}...", this.getClass().getSimpleName());
        try {
            List<Class<?>> classes = findClasses(ENTITY_CLASSPATH);
            classes.addAll(findClasses(DOMAIN_CLASSPATH));
            log.debug(">>>classes: {}", classes);
            this.tableFieldMappings(classes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 表字段映射
     *
     * @param classes
     */
    private void tableFieldMappings(List<Class<?>> classes) {
        classes.forEach(clazz -> {
            List<Field> list = TableInfoHelper.getAllFields(clazz);
            list.forEach(field -> {
                TableField tableField = field.getAnnotation(TableField.class);
                String metaColName;
                if (Objects.nonNull(tableField) && StringUtils.isNotBlank(metaColName = tableField.value()) && metaColName.contains("`")) {

                    String newColName = DbConfig.replaceAll(metaColName, getReplaceStr());

                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(tableField);
                    try {
                        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
                        memberValues.setAccessible(true);
                        Map<String, Object> memberValuesMap = (Map<String, Object>) memberValues.get(invocationHandler);
                        memberValuesMap.put("value", newColName);
                        log.info("将实体类映射字段{}修改为{}", metaColName, newColName);
                    } catch (NoSuchFieldException | IllegalAccessException exception) {
                        throw new RuntimeException(exception);
                    }
                }
            });
        });
    }


    private List<Class<?>> findClasses(String classpath) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //找到所有实体类的class
        Resource[] resources = resolver.getResources(classpath);
        return Arrays.stream(resources).map(res -> {
                    try {
                        // 先获取resource的元信息，然后获取class元信息，最后得到 class 全路径
                        String clsName = new SimpleMetadataReaderFactory().getMetadataReader(res).getClassMetadata().getClassName();
                        return Class.forName(clsName);
                    } catch (IOException | ClassNotFoundException e) {
                        log.debug(">>>>>找不到class文件:", e);
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
