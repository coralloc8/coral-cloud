package com.coral.simple.web1.util;

import java.util.*;

import com.coral.simple.web1.service.NumCreatorService;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * @description: 获取spring.factories中的属性配置
 * @author: huss
 * @time: 2020/10/24 15:10
 */
public class SpringFactoriesLoaderTest {

    public static void main(String[] args) {
        loadFactoryNames();
        loadFactories();
    }

    /**
     * 获取指定接口的实现类的路径
     */
    private static void loadFactoryNames() {
        List<String> list = SpringFactoriesLoader.loadFactoryNames(NumCreatorService.class, null);
        System.out.println("interface [NumCreatorService] impl is:" + list);
    }

    /**
     * 获取指定接口类的实现
     */
    private static void loadFactories() {
        List<NumCreatorService> list = SpringFactoriesLoader.loadFactories(NumCreatorService.class, null);
        System.out.println("interface [NumCreatorService] impl is:" + list);
    }

}
