package com.coral.cloud.monitor.runner;

import com.coral.base.common.opendoc.config.OpenApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className InitRunner
 * @description 初始化
 * @date 2021/9/23 13:48
 */
@Component
@Slf4j
public class InitRunner implements ApplicationRunner {
    @Autowired
    private OpenApiConfig openApiConfig;

    @Autowired
    private JobScheduleRefreshTask jobScheduleRefreshTask;


    @Override
    public void run(ApplicationArguments args) {
        log.info(">>>>>init...");
        try {
            openApiConfig.printVersion();
            jobScheduleRefreshTask.initJobs();

        } catch (Exception e) {
            log.error("Init Error", e);
        }
    }


}
