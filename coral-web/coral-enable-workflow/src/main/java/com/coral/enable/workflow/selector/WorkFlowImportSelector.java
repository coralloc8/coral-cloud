package com.coral.enable.workflow.selector;

import com.coral.enable.workflow.EnableWorkFlowAutoConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 工作流自动装配
 * @author: huss
 * @time: 2020/12/29 14:24
 */
public class WorkFlowImportSelector implements ImportSelector {

    private final static String DEFAULT_CLASS_NAME = EnableWorkFlowAutoConfiguration.class.getName();

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {DEFAULT_CLASS_NAME,};
    }

}
