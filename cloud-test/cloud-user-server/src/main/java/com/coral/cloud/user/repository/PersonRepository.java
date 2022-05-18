package com.coral.cloud.user.repository;

import com.coral.cloud.user.entity.PersonDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className PersonRepository
 * @description person repository
 * @date 2022/4/28 11:06
 */
public interface PersonRepository extends ElasticsearchRepository<PersonDocument, Long> {

    /**
     * 查询用户列表
     *
     * @param personName
     * @return
     */
    List<PersonDocument> findByPersonNameLike(String personName);


}
