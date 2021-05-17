package com.coral.simple.web1.service.impl;

import com.coral.base.common.StringUtils;
import com.coral.base.common.jpa.service.impl.JpaBaseServiceImpl;
import com.coral.base.common.jpa.util.dsl.PredicateCreator;
import com.coral.database.test.jpa.entity.QTest;
import com.coral.database.test.jpa.entity.Test;
import com.coral.database.test.jpa.repository.TestRepository;
import com.coral.simple.web1.service.TestService;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className TestServiceImpl
 * @description test
 * @date 2021/5/13 10:35
 */
@Service
public class TestServiceImpl extends JpaBaseServiceImpl<Test, Long, TestRepository> implements TestService {
    @Override
    public List<Test> findAll(String name, Integer age) {
        Predicate predicate = PredicateCreator.builder()
                .link(StringUtils.isNotBlank(name) ? QTest.test.name.eq(name) : null)
                .link(Objects.nonNull(age) ?  QTest.test.age.eq(age) : null)
                .build();

        return findAll(predicate);
    }

}
