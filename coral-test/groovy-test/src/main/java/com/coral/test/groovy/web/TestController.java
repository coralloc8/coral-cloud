package com.coral.test.groovy.web;

import com.coral.test.groovy.UserInfoDTO;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.coral.web.core.support.SpringContext;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author huss
 * @version 1.0
 * @className TestController
 * @description todo
 * @date 2022/11/26 8:50
 */
@Slf4j
@RestController
public class TestController {


    @SneakyThrows
    @GetMapping("/test")
    public Result<String> hello(String name) {
        Binding binding = new Binding(SpringContext.getAllBeans());

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserNo("001");
        userInfoDTO.setUserName(name);

        binding.setProperty("user", userInfoDTO);

        binding.setProperty("name", name);
        GroovyShell groovyShell = new GroovyShell(binding);
        groovyShell.getClassLoader().addClasspath("E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\third\\MQSDK_JAVA.jar");
//        groovyShell.getClassLoader().addClasspath("E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\third\\SDKConfig.properties");

        String scriptContent = "def run(name) {println '入参：' + name;return testService.test(name)};run(name);";
        String result = (String) groovyShell.parse(scriptContent).run();

      groovyShell.parse(new File("E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\third\\EwellServiceToolTest.groovy")).run();


        return new Results().success("hello " + result);
    }

//    private void loadResources() {
//        try {
//            File file = new File("E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\third\\MQSDK_JAVA.jar");
//            URL[] urls = new URL[]{file.toURI().toURL()};
//            URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
//            JarFile jarFile = new JarFile(file);
//            for (Enumeration<JarEntry> ea = jarFile.entries(); ea.hasMoreElements(); ) {
//                JarEntry jarEntry = ea.nextElement();
//                String name = jarEntry.getName();
//                boolean filter = name.startsWith("META-INF") ||
//                        name.endsWith(".txt") ||
//                        name.equals("AUTHORS") ||
//                        name.equals("LICENSE") ||
//                        !name.contains(".");
//
//                boolean isClass = name.endsWith(".class");
//
//                if (StringUtils.isNotBlank(name) && !filter && isClass) {
//                    log.info(">>>>>加载name:{}", name);
//                    if (!jarEntry.isDirectory()) {
//                        String loadName = name.replace("/", ".").substring(0, name.length() - 6);
//                        log.info(">>>>>加载loadName:{}", loadName);
//
//                        String target = "com.ewell.sdk.business.EwellServiceToolTest";
//
//                        if (target.equals(loadName)) {
//                            log.info("$$$$$$$$$$$ loadName： {} $$$$$$$$$$$", loadName);
//                            //加载class
//                            Class<?> c = classLoader.loadClass(loadName);
//                        }
//
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw Exceptions.unchecked(e);
//        }
//
//
//    }


}
