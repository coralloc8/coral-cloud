package com.coral.simple.web1.service.impl;

import com.coral.base.common.StringUtils;
import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.base.common.exception.SystemException;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.base.common.jpa.bo.JpaPage;
import com.coral.base.common.jpa.service.impl.JpaBaseServiceImpl;
import com.coral.base.common.jpa.util.dsl.PredicateCreator;
import com.coral.database.test.jpa.primary.entity.QTest;
import com.coral.database.test.jpa.primary.entity.Test;
import com.coral.database.test.jpa.primary.repository.TestRepository;
import com.coral.database.test.jpa.secondary.entity.QSecTest;
import com.coral.database.test.jpa.secondary.entity.SecTest;
import com.coral.database.test.jpa.secondary.repository.SecTestRepository;
import com.coral.simple.web1.service.TestService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private SecTestRepository secTestRepository;


    @Override
    public Map<String, Object> findAll3(String name, Integer age) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("primary", findAll(name, age));
        map.put("secondary", findAll2(name, age));
        return map;
    }

    @Override
    public Page<SecTest> findAll2(String name, Integer age) {
        Predicate predicate = PredicateCreator.builder()
                .link(StringUtils.isNotBlank(name) ? QSecTest.secTest.name.eq(name) : null)
                .link(Objects.nonNull(age) ? QSecTest.secTest.age.eq(age) : null)
                .build();
        Pageable pageable = JpaPage.of(5, 1).buildPageable();
        if (Objects.isNull(predicate)) {
            return secTestRepository.findAll(pageable);
        }
        return secTestRepository.findAll(predicate, pageable);
    }

    @Override
    public List<Test> findAll(String name, Integer age) {
        Predicate predicate = PredicateCreator.builder()
                .link(StringUtils.isNotBlank(name) ? QTest.test.name.eq(name) : null)
                .link(Objects.nonNull(age) ? QTest.test.age.eq(age) : null)
                .build();

        return findAll(predicate);
    }

    @Override
    public void save(String name, Integer age) {
        Test test = new Test();
        test.setName(name);
        test.setAge(age);
        test.setMoney(crateMoney());
        test.setCreateTime(LocalDateTime.now());
        save(test);
    }

    @Override
    public void save2(String name, Integer age) {
        SecTest test = new SecTest();
        test.setName(name);
        test.setAge(age);
        test.setMoney(crateMoney());
        test.setCreateTime(LocalDateTime.now());
        secTestRepository.saveAndFlush(test);
    }

    @Override
    public void save3(String name, Integer age) throws SystemException {
        save(name, age);
        if (age < 10) {
            throw new SystemException(BaseErrorMessageEnum.ILLEGAL_PARAMETER);
        }
        save2(name, age);
    }

    @Override
    public void save4(String name, Integer age) {
        save(name, age);
        save2(name, age);
        if (age < 10) {
            throw new SystemRuntimeException(BaseErrorMessageEnum.ILLEGAL_PARAMETER);
        }
    }

    private Double crateMoney() {
        double money = Math.random() * 100000000;
        BigDecimal bg = new BigDecimal(money);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

}
