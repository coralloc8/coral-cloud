package com.coral.cloud.user.runner;

import com.coral.cloud.user.common.config.DocumentIndexProperty;
import com.coral.cloud.user.entity.userrecord.HospitalUserRecordMzDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className HospitalUserRecordMzIndexRunner
 * @description 创建 HospitalUserRecordMzIndex 索引
 * @date 2022/5/24 9:31
 */
@Slf4j
@Component
public class HospitalUserRecordMzIndexRunner implements ApplicationRunner {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private DocumentIndexProperty documentIndexProperty;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(">>>>>开始初始化 HospitalUserRecordMzIndexRunner...");

        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(HospitalUserRecordMzDocument.class);

        boolean exists = indexOperations.exists();
        if (exists) {
            return;
        }
        indexOperations.create();
        indexOperations.putMapping();

        //添加写入别名  后续的数据添加都往写入别名中写入
        AliasActions aliasActions = new AliasActions(new AliasAction.Add(
                AliasActionParameters.builder()
                        .withIndices(documentIndexProperty.getHospitalUserRecordMz().getInitIndex())
                        .withAliases(documentIndexProperty.getHospitalUserRecordMz().getWrite())
                        .withIsWriteIndex(true)
                        .build()
        ));
        indexOperations.alias(aliasActions);
        log.info(">>>>>初始化完成 HospitalUserRecordMzIndexRunner...");
    }
}
