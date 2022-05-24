package com.coral.cloud.user;

import com.coral.base.common.DateTimeUtil;
import com.coral.base.common.json.JsonUtil;
import com.coral.base.common.reflection.LambdaFieldUtil;
import com.coral.cloud.user.entity.userrecord.HospitalUserRecordMzSearchDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author huss
 * @version 1.0
 * @className ElasticService
 * @description es测试
 * @date 2022/5/13 13:46
 */
@Slf4j
@SpringBootTest
public class ElasticService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 现病史
     */
    private String nowMedicalHistoryKey;
    /**
     * 主诉
     */
    private String mainSuitKey;

    /**
     * 主键
     */
    private String idKey;

    /**
     * 创建时间
     */
    private String createTimeKey;

    /**
     * 就诊时间
     */
    private String outPatientTimeKey;


    @BeforeEach
    void init() {
        nowMedicalHistoryKey = LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getNowMedicalHistory);
        mainSuitKey = LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getMainSuit);
        idKey = LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getId);
        createTimeKey = LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getCreateTime);
        outPatientTimeKey = LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getOutPatientTime);
    }


    /**
     * 查询现病史中包含 流鼻涕的 患者病历记录
     * <p>
     * 流鼻涕的同义词有 流涕  清涕
     * <p>
     * 同时要注意排除没有流鼻涕的患者病历记录
     * <p>
     * elastic_query1.json
     */
    @Test
    @DisplayName("查询现病史中包含[流鼻涕]的患者病历记录")
    public void query1() {
        //查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(
                        QueryBuilders.boolQuery()
                                .should(QueryBuilders.matchQuery(nowMedicalHistoryKey, "流涕"))
                                .should(QueryBuilders.matchQuery(nowMedicalHistoryKey, "清涕").operator(Operator.AND))
                ).filter(
                        QueryBuilders.boolQuery()
                                .mustNot(QueryBuilders.matchPhraseQuery(nowMedicalHistoryKey, "无流涕"))
                                .mustNot(QueryBuilders.matchPhraseQuery(nowMedicalHistoryKey, "暂无流涕"))
                );
        //需要查看的字段列表
        String[] includes = new String[]{mainSuitKey, nowMedicalHistoryKey, idKey};
        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(includes, null);
        //排序字段
        SortBuilder createTimeSort = SortBuilders.fieldSort(createTimeKey).order(SortOrder.DESC);
        //高亮显示
        HighlightBuilder.Field highlightField = new HighlightBuilder.Field(nowMedicalHistoryKey)
                .preTags("<font color='red'>")
                .postTags("</font>");


        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withSourceFilter(fetchSourceFilter)
                .withSorts(createTimeSort)
                .withHighlightFields(highlightField)
                .withPageable(PageRequest.of(0, 10))
                .build();

        log.info(">>>>>query dsl:\n{}", query.getQuery());
        log.info(">>>>>source dsl:\n{}", JsonUtil.toJson(query.getSourceFilter()));
        log.info(">>>>>sort dsl:\n{}", query.getElasticsearchSorts());
        log.info(">>>>>highlight dsl:\n{}", query.getHighlightFields());
        log.info(">>>>>page dsl:\n{}", query.getPageable());

        SearchHits<HospitalUserRecordMzSearchDocument> documentSearchHits = elasticsearchRestTemplate.search(query, HospitalUserRecordMzSearchDocument.class);
        documentSearchHits.forEach(System.out::println);
    }

    /**
     * 查询[就诊日期为2022-05-08]的患者病历记录
     * elastic_query2.json
     */
    @Test
    @DisplayName("查询[就诊日期为2022-05-08]的患者病历记录")
    public void query2() {
        LocalDate localDate = LocalDate.of(2022, 5, 8);
        String start = DateTimeUtil.format(LocalDateTime.of(localDate, LocalTime.MIN), DateFormat.date_hour_minute_second_millis.getPattern());
        String end = DateTimeUtil.format(LocalDateTime.of(localDate, LocalTime.MAX), DateFormat.date_hour_minute_second_millis.getPattern());

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery(outPatientTimeKey)
                        .gte(start)
                        .lte(end)
                ).must(
                        QueryBuilders.boolQuery()
                                .should(QueryBuilders.matchQuery(nowMedicalHistoryKey, "流涕"))
                                .should(QueryBuilders.matchQuery(nowMedicalHistoryKey, "清涕").operator(Operator.AND))
                );

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();
        log.info(">>>>>query dsl:\n{}", query.getQuery());

        SearchHits<HospitalUserRecordMzSearchDocument> hits = elasticsearchRestTemplate.search(query, HospitalUserRecordMzSearchDocument.class);

        hits.forEach(System.out::println);
    }

    /**
     * 查询[患者门诊号为8008]的患者病历记录
     * elastic_query3.json
     */
    @Test
    @DisplayName("查询[患者门诊号为8008]的患者病历记录")
    public void query3() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery("8008",
                                LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getOutPatientNumber),
                                LambdaFieldUtil.getFieldName(HospitalUserRecordMzSearchDocument::getOutPatientNo)
                        )
                );

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        log.info(">>>>>query dsl:\n{}", query.getQuery());

        SearchHits<HospitalUserRecordMzSearchDocument> hits = elasticsearchRestTemplate.search(query, HospitalUserRecordMzSearchDocument.class);

        hits.forEach(System.out::println);
    }


}
