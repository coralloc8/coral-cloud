package com.coral.test.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author huss
 * @version 1.0
 * @className GroovyTest
 * @description todo
 * @date 2022/11/28 14:55
 */
public class GroovyTest {
    final String BASE_PATH = "E:\\projects\\mine\\java\\coral-cloud\\coral-test\\groovy-test\\src\\test\\java\\com\\coral\\test\\groovy";

    @Test
    @DisplayName("groovy test1")
    public void test1() throws IOException {
        GroovyShell groovyShell = new GroovyShell(new Binding());
        groovyShell.parse(Paths.get(BASE_PATH, "GroovyTest1.groovy").toUri()).run();
    }
}
