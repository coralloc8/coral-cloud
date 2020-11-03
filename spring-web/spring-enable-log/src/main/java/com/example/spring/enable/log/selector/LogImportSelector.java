package com.example.spring.enable.log.selector;

import com.example.spring.enable.log.EnableLogAutoConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 自定义启动
 * @author: huss
 * @time: 2020/10/21 16:31
 */
public class LogImportSelector implements ImportSelector {

    private final static String DEFAULT_BASE_PACKAGE = EnableLogAutoConfiguration.class.getPackage().getName();

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {EnableLogAutoConfiguration.class.getName()};
    }

    // @Override
    // public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    //
    // ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
    // // 这里实现包含,相当@ComponentScan includeFilters
    // // scanner.addIncludeFilter(new AssignableTypeFilter(Object.class));
    // // 这里可以实现排除，相当@ComponentScan excludeFilters
    // // scanner.addExcludeFilter(new AssignableTypeFilter(Object.class));
    // Set<String> classes = new HashSet<>();
    // Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(DEFAULT_BASE_PACKAGE);
    // candidateComponents.forEach(e -> {
    // classes.add(e.getBeanClassName());
    // });
    // return classes.toArray(new String[classes.size()]);
    //
    // }

}
