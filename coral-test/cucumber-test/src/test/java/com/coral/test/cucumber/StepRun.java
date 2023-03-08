package com.coral.test.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * @author huss
 * @version 1.0
 * @className StepRun
 * @description todo
 * @date 2023/3/3 8:29
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
//@CucumberOptions(features = "src/test/resources/features",
//        plugin = {
//                "pretty", //格式化插件
//                "html:target/html-report.html",
//                "json:target/json-report/run.json"
//        }
//)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.coral.test.cucumber.features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/html-report.html,json:target/json-report/run.json")
public class StepRun {

}
