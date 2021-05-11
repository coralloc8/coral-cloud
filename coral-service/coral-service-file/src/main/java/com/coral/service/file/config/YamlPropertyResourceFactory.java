package com.coral.service.file.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

/**
 * @author huss
 */
public class YamlPropertyResourceFactory implements PropertySourceFactory {

    /**
     * Create a {@link PropertySource} that wraps the given resource.
     *
     * @param name
     *            the name of the property source
     * @param encodedResource
     *            the resource (potentially encoded) to wrap
     * @return the new {@link PropertySource} (never {@code null})
     * @throws IOException
     *             if resource resolution failed
     */
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource encodedResource)
        throws IOException {
        String resourceName = Optional.ofNullable(name).orElse(encodedResource.getResource().getFilename());
        // yaml资源文件
        if (resourceName.endsWith(".yml") || resourceName.endsWith(".yaml")) {
            List<PropertySource<?>> yamlSources;
            yamlSources = new YamlPropertySourceLoader().load(resourceName, encodedResource.getResource());
            return yamlSources.get(0);
        } else {// 返回空的Properties
            return new PropertiesPropertySource(resourceName, new Properties());
        }
    }

}