package com.coral.cloud.user.service;

import com.coral.cloud.user.common.config.DocumentIndexProperty;
import com.coral.cloud.user.entity.userrecord.HospitalUserRecordMzSearchDocument;
import com.coral.cloud.user.entity.userrecord.HospitalUserRecordMzWriteDocument;
import com.coral.cloud.user.repository.HospUserRecordMzSearchRepository;
import com.coral.cloud.user.repository.HospUserRecordMzWriteRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className HospitalUserRecordMzWriteService
 * @description 写入
 * @date 2022/5/24 9:18
 */
@Service
public class HospitalUserRecordMzWriteService {


    @Autowired
    private HospUserRecordMzSearchRepository hospUserRecordMzSearchRepository;

    @Autowired
    private HospUserRecordMzWriteRepository hospUserRecordMzWriteRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private DocumentIndexProperty indexProperty;

    /**
     * 保存文档
     *
     * @param documents
     */
    public void save(List<HospitalUserRecordMzWriteDocument> documents) {
        //先全文档删除旧数据
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.idsQuery()
                        .addIds(documents.stream()
                                .map(e -> String.valueOf(e.getId()))
                                .collect(Collectors.toSet())
                                .toArray(new String[]{})
                        )
                )
                .build();
        elasticsearchRestTemplate.delete(query, HospitalUserRecordMzSearchDocument.class);

        documents.forEach(e -> {
            //新增
            elasticsearchRestTemplate.save(e);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void save2(List<HospitalUserRecordMzWriteDocument> documents) {
        hospUserRecordMzSearchRepository.deleteAllById(documents.stream()
                .map(HospitalUserRecordMzWriteDocument::getId).collect(Collectors.toSet())
        );
        documents.forEach(e -> {
            hospUserRecordMzWriteRepository.save(e);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
}
