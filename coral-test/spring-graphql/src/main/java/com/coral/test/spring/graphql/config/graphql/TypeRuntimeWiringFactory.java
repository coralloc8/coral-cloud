package com.coral.test.spring.graphql.config.graphql;

import cn.hutool.core.util.ClassUtil;
import com.coral.test.spring.graphql.eums.BaseEnum;
import com.coral.test.spring.graphql.util.EnumFactory;
import graphql.schema.idl.TypeRuntimeWiring;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类型转换器
 *
 * @author huss
 * @date 2023/12/29 14:41
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className EnumValuesProvider
 */
@Slf4j
public class TypeRuntimeWiringFactory {

    private static final Set<String> SCAN_PACKAGES = Set.of("com.coral");

    // graphql.schema.GraphQLEnumType.getNameByValue
    public static List<TypeRuntimeWiring> autoCreate() {
        return SCAN_PACKAGES.parallelStream().flatMap(path -> {
            Set<Class<?>> classes = ClassUtil.scanPackageBySuper(path, BaseEnum.class);
            log.debug(">>>>>[scanPackageBySuper] 结果集:{}", classes);
            return classes.stream().map(clazz -> {
                if (BaseEnum.class.isAssignableFrom(clazz)) {
                    Class<BaseEnum> enumClazz = (Class<BaseEnum>) clazz;
                    return create(enumClazz);
                }
                return null;
            });
        }).collect(Collectors.toList());
    }

    public static <T extends BaseEnum<?>> TypeRuntimeWiring create(Class<T> clazz) {


        return TypeRuntimeWiring.newTypeWiring(clazz.getSimpleName(), typeBuilder -> {
            log.info(">>>>>[typeBuilder] start.");
            return typeBuilder.enumValues(name -> {
                Optional<T> res = EnumFactory.findBy(name, clazz);
                if (res.isEmpty()) {
                    log.error("#####[ERROR].枚举解析失败,原始值:{},clazz:{}", name, clazz);
                    return null;
                }
                return res.get();
            });
        });
    }

    public static void main(String[] args) {

    }

}
