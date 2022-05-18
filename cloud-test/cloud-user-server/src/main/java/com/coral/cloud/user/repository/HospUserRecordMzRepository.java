package com.coral.cloud.user.repository;

import com.coral.cloud.user.entity.HospitalUserRecordMzDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author huss
 * @version 1.0
 * @className HospUserRecordMzRepository
 * @description person repository
 * @date 2022/4/28 11:06
 */
public interface HospUserRecordMzRepository extends ElasticsearchRepository<HospitalUserRecordMzDocument, Long> {

}
