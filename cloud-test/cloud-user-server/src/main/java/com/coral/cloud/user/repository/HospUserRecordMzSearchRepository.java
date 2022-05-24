package com.coral.cloud.user.repository;

import com.coral.cloud.user.entity.userrecord.HospitalUserRecordMzSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author huss
 * @version 1.0
 * @className HospUserRecordMzSearchRepository
 * @description user record mz repository
 * @date 2022/4/28 11:06
 */
public interface HospUserRecordMzSearchRepository extends ElasticsearchRepository<HospitalUserRecordMzSearchDocument, Long> {

}
