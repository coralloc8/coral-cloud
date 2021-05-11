package com.coral.enable.task.selector;

import com.coral.enable.task.EnableTaskAutoConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 自定义启动
 * @author: huss
 * @time: 2020/10/21 16:31
 */
public class TaskImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {EnableTaskAutoConfiguration.class.getName()};
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
